package jp.co.project.planets.earthly.common.logic;

import java.time.Clock;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jp.co.project.planets.earthly.schema.db.entity.OauthClient;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientScope;
import jp.co.project.planets.earthly.schema.repository.OAuthClientManagementRepository;
import jp.co.project.planets.earthly.schema.repository.OAuthClientRepository;
import jp.co.project.planets.earthly.schema.repository.OAuthClientScopeRepository;

/**
 * oauth client logic
 */
@Component
public class OAuthClientLogic {

    private static final Logger log = LoggerFactory.getLogger(OAuthClientLogic.class);
    public final OAuthClientRepository oauthClientRepository;
    public final OAuthClientScopeRepository oauthClientScopeRepository;
    public final OAuthClientManagementRepository oauthClientManagementRepository;
    private final CryptoLogic cryptoLogic;

    public OAuthClientLogic(final CryptoLogic cryptoLogic, final OAuthClientRepository oauthClientRepository,
            final OAuthClientScopeRepository oauthClientScopeRepository,
            final OAuthClientManagementRepository oauthClientManagementRepository) {
        this.cryptoLogic = cryptoLogic;
        this.oauthClientRepository = oauthClientRepository;
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
        for (final String scope : scopes) {
            final var oauthClientScope = new OauthClientScope(null, id, scope);
            oauthClientScopeRepository.insert(oauthClientScope);
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
}
