package jp.co.project.planets.earthly.common.logic;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jp.co.project.planets.earthly.core.account.Account;
import jp.co.project.planets.earthly.schema.db.entity.OauthClient;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientGrantType;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientScope;
import jp.co.project.planets.earthly.schema.emuns.GrantType;
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
     * @param account
     *            操作ユーザー
     * @return OAuthクライアントID
     */
    public String create(final String name, final List<String> scopes, final Account account) {

        final var entryOAuthClient = generateOAuthClient(name, account);
        final var result = oauthClientRepository.insert(entryOAuthClient);
        if (result < 1) {
            log.error("OAuthクライアントの登録に失敗しました。");
            return null;
        }
        final var oauthClientOptional = oauthClientRepository.findAccessibleByName(name, account);
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
        oauthClientManagementRepository.insert(id, account.id());
        return id;
    }

    /**
     * OAuthクライアント生成
     *
     * @param name
     *            OAuthクライアント名
     * @param account
     *            操作ユーザー
     * @return OauthClient
     */
    OauthClient generateOAuthClient(final String name, final Account account) {
        final var currentTime = Instant.now(Clock.systemUTC()).toEpochMilli();
        final var clientId = cryptoLogic.encodeSHA256(String.valueOf(currentTime));
        final var planText = name + "-" + currentTime;
        final var secret = cryptoLogic.encodeSHA256(planText);
        return new OauthClient(null, name, clientId, secret, null, account.id(), null,
                account.id(), false);
    }

    /**
     * 操作ユーザーが対象のOAuthクライアントを閲覧できるか
     * 
     * @param id
     *            OAuthクライアントID
     * @param account
     *            ユーザー情報
     * @return true:閲覧可能 false:閲覧不可
     */
    public boolean canAccessibleClient(final String id, final Account account) {

        if (account.permissions().contains(PermissionEnum.VIEW_ALL_OAUTH_CLIENT)) {
            return true;
        }

        return isManagementUser(id, account.id());
    }

    /**
     * 操作ユーザーが編集権限があるか
     * 
     * @param id
     *            OAuthクライアントID
     * @param account
     *            ユーザー情報
     * @return true:編集可能 false:編集不可
     */
    public boolean canEditableClient(final String id, final Account account) {
        if (account.permissions().contains(PermissionEnum.EDIT_OAUTH_CLIENT)) {
            return true;
        }
        return isManagementUser(id, account.id());
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
