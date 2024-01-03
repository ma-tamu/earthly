package jp.co.project.planets.earthly.schema.repository;

import org.seasar.doma.jdbc.criteria.NativeSql;
import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.OAuthClientScopeDao;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientScope;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientScope_;

/**
 * oauth client scope repository
 */
@Repository
public class OAuthClientScopeRepository {

    private final OAuthClientScopeDao oauthClientScopeDao;
    private final NativeSql nativeSql;

    public OAuthClientScopeRepository(final OAuthClientScopeDao oauthClientScopeDao, final NativeSql nativeSql) {
        this.oauthClientScopeDao = oauthClientScopeDao;
        this.nativeSql = nativeSql;
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

    /**
     * delete by oauth client id
     * 
     * @param clientId
     *            oauth client id
     * @return delete count
     */
    public int deleteByClientId(final String clientId) {
        final var oauthClientScope = new OauthClientScope_();
        return nativeSql.delete(oauthClientScope).where(w -> w.eq(oauthClientScope.oauthClientId, clientId)).execute();
    }
}
