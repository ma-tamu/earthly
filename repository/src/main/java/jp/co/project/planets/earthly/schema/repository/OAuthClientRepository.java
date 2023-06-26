package jp.co.project.planets.earthly.schema.repository;

import java.util.Objects;

import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.GrantTypeDao;
import jp.co.project.planets.earthly.schema.db.dao.LogoutRedirectUrlDao;
import jp.co.project.planets.earthly.schema.db.dao.OAuthClientDao;
import jp.co.project.planets.earthly.schema.db.dao.RedirectUriDao;
import jp.co.project.planets.earthly.schema.db.dao.ScopeDao;
import jp.co.project.planets.earthly.schema.db.entity.OauthClient;
import jp.co.project.planets.earthly.schema.model.entity.OAuthClientEntity;

/**
 * oauth client repository
 */
@Repository
public class OAuthClientRepository {

    private final OAuthClientDao oauthClientDao;
    private final GrantTypeDao grantTypeDao;
    private final ScopeDao scopeDao;
    private final RedirectUriDao redirectUriDao;

    private final LogoutRedirectUrlDao logoutRedirectUrlDao;

    public OAuthClientRepository(final OAuthClientDao oauthClientDao, final GrantTypeDao grantTypeDao,
            final ScopeDao scopeDao, final RedirectUriDao redirectUriDao,
            final LogoutRedirectUrlDao logoutRedirectUrlDao) {
        this.oauthClientDao = oauthClientDao;
        this.grantTypeDao = grantTypeDao;
        this.scopeDao = scopeDao;
        this.redirectUriDao = redirectUriDao;
        this.logoutRedirectUrlDao = logoutRedirectUrlDao;
    }

    /**
     * find by id
     *
     * @param id
     *            id
     * @return OAuthClientEntity
     */
    public OAuthClientEntity findById(final String id) {
        final var oauthClient = oauthClientDao.selectById(id);
        if (Objects.isNull(oauthClient)) {
            return null;
        }
        return generateOAuthClientEntity(oauthClient);
    }

    /**
     * find by client id
     *
     * @param clientId
     *            client id
     * @return OAuthClientEntity
     */
    public OAuthClientEntity findByClientId(final String clientId) {
        return oauthClientDao.selectByClientId(clientId).map(this::generateOAuthClientEntity).orElse(null);
    }

    /**
     * OAuthClientEntityを生成
     *
     * @param oauthClient
     *            OAuthクライアント
     * @return OAuthClientEntity
     */
    private OAuthClientEntity generateOAuthClientEntity(final OauthClient oauthClient) {
        final var id = oauthClient.getId();
        final var grantTypes = grantTypeDao.selectByClientId(id);
        final var scopes = scopeDao.selectByClientId(id);
        final var redirectUris = redirectUriDao.selectByClientId(id);
        final var logoutRedirectUrls = logoutRedirectUrlDao.selectByClientId(id);

        return new OAuthClientEntity(id, oauthClient.getClientId(), oauthClient.getClientSecret(),
                oauthClient.getName(), scopes, grantTypes, redirectUris, logoutRedirectUrls);
    }
}
