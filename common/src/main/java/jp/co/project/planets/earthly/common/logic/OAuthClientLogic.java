package jp.co.project.planets.earthly.common.logic;

import java.time.Clock;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jp.co.project.planets.earthly.common.enums.GrantType;
import jp.co.project.planets.earthly.schema.db.entity.OauthClient;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientGrantType;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientScope;
import jp.co.project.planets.earthly.schema.emuns.PermissionEnum;
import jp.co.project.planets.earthly.schema.repository.OAuthClientGrantTypeRepository;
import jp.co.project.planets.earthly.schema.repository.OAuthClientManagementRepository;
import jp.co.project.planets.earthly.schema.repository.OAuthClientRepository;
import jp.co.project.planets.earthly.schema.repository.OAuthClientScopeRepository;

/**
 * oauth client logic
 */
@Component
public class OAuthClientLogic {

    private static final Logger log = LoggerFactory.getLogger(OAuthClientLogic.class);
    private final OAuthClientRepository oauthClientRepository;
    private final OAuthClientGrantTypeRepository oauthClientGrantTypeRepository;
    private final OAuthClientScopeRepository oauthClientScopeRepository;
    private final OAuthClientManagementRepository oauthClientManagementRepository;
    private final CryptoLogic cryptoLogic;

    public OAuthClientLogic(final CryptoLogic cryptoLogic, final OAuthClientRepository oauthClientRepository,
            final OAuthClientGrantTypeRepository oauthClientGrantTypeRepository,
            final OAuthClientScopeRepository oauthClientScopeRepository,
            final OAuthClientManagementRepository oauthClientManagementRepository) {
        this.cryptoLogic = cryptoLogic;
        this.oauthClientRepository = oauthClientRepository;
        this.oauthClientGrantTypeRepository = oauthClientGrantTypeRepository;
        this.oauthClientScopeRepository = oauthClientScopeRepository;
        this.oauthClientManagementRepository = oauthClientManagementRepository;
    }

    /**
     * OAuthクライアント登録
     *
     * @param name
     *            OAuthクライアント名
     * @param scopes
     *            スコープ
     * @param operationUserId
     *            操作ユーザーID
     * @return OAuthクライアントID
     */
    public String create(final String name, final List<String> scopes, final String operationUserId) {

        final var entryOAuthClient = generateOAuthClient(name, operationUserId);
        final int insertCount = oauthClientRepository.insert(entryOAuthClient);
        if (insertCount < 1) {
            log.error("OAuthクライアントの登録に失敗しました。");
            return null;
        }
        final var oauthClientOptional = oauthClientRepository.findAccessibleByName(name, Collections.emptyList(),
                operationUserId);
        if (oauthClientOptional.isEmpty()) {
            log.error("OAuthクライアント名からの取得に失敗しました。 name:{}", name);
            return null;
        }
        final var oauthClient = oauthClientOptional.get();
        final var id = oauthClient.getId();
        for (final var scope : scopes) {
            final var oauthClientScope = new OauthClientScope(null, id, scope);
            oauthClientScopeRepository.insert(oauthClientScope);
        }
        for (final var grantType : GrantType.values()) {
            final var oauthClientGrantType = new OauthClientGrantType(null, id, grantType.getId());
            oauthClientGrantTypeRepository.insert(oauthClientGrantType);
        }
        oauthClientManagementRepository.insert(id, operationUserId);
        return id;
    }

    /**
     * OAuthクライアント生成
     *
     * @param name
     *            OAuthクライアント名
     * @param operationUserId
     *            操作ユーザーID
     * @return OauthClient
     */
    OauthClient generateOAuthClient(final String name, final String operationUserId) {
        final var currentTime = Instant.now(Clock.systemUTC()).toEpochMilli();
        final var clientId = cryptoLogic.encodeSHA256(String.valueOf(currentTime));
        final var planText = name + "-" + currentTime;
        final var secret = cryptoLogic.encodeSHA256(planText);
        return new OauthClient(null, name, clientId, secret, null, operationUserId, null,
                operationUserId, false);
    }

    /**
     * 操作ユーザーが対象のOAuthクライアントを閲覧できるか
     * 
     * @param id
     *            OAuthクライアントID
     * @param permissionEnumList
     *            パーミッションリスト
     * @param operationUserId
     *            ユーザーID
     * @return true:閲覧可能 false:閲覧不可
     */
    public boolean canAccessibleClient(final String id, final List<PermissionEnum> permissionEnumList,
            final String operationUserId) {

        if (permissionEnumList.contains(PermissionEnum.VIEW_ALL_OAUTH_CLIENT)) {
            return true;
        }

        return isManagementUser(id, operationUserId);
    }

    /**
     * 操作ユーザーが編集権限があるか
     * 
     * @param id
     *            OAuthクライアントID
     * @param permissionEnumList
     *            パーミッションリスト
     * @param operationUserId
     *            ユーザーID
     * @return true:編集可能 false:編集不可
     */
    public boolean canEditableClient(final String id, final List<PermissionEnum> permissionEnumList,
            final String operationUserId) {
        if (permissionEnumList.contains(PermissionEnum.EDIT_OAUTH_CLIENT)) {
            return true;
        }
        return isManagementUser(id, operationUserId);
    }

    /**
     * 対象OAuthクライアントの管理者か
     * 
     * @param id
     *            OAuthクライアントID
     * @param operationUserId
     *            ユーザーID
     * @return true:管理者 false:管理者でない
     */
    boolean isManagementUser(final String id, final String operationUserId) {
        final var oauthClientManagementOptional = oauthClientManagementRepository.findByOAuthClientIdAndUserId(id,
                operationUserId);
        return oauthClientManagementOptional.isPresent();
    }
}
