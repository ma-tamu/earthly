package jp.co.project.planets.earthly.schema.repository;

import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.OAuthClientScopeDao;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientScope;

/**
 * oauth client scope repository
 */
@Repository
public class OAuthClientScopeRepository {

    public final OAuthClientScopeDao oauthClientScopeDao;

    public OAuthClientScopeRepository(final OAuthClientScopeDao oauthClientScopeDao) {
        this.oauthClientScopeDao = oauthClientScopeDao;
    }

    /**
     * insert oauth client scope
     *
     * @param oauthClientScope
     *            oauth client scope
     * @return insert count
     */
    public int insert(final OauthClientScope oauthClientScope) {
        return oauthClientScopeDao.insert(oauthClientScope);
    }
}
