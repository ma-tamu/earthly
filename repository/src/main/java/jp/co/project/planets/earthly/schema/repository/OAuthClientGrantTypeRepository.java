package jp.co.project.planets.earthly.schema.repository;

import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.OauthClientGrantTypeDao;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientGrantType;

/**
 * oauth client grant type repository
 */
@Repository
public class OAuthClientGrantTypeRepository {

    private final OauthClientGrantTypeDao oauthClientGrantTypeDao;

    public OAuthClientGrantTypeRepository(final OauthClientGrantTypeDao oauthClientGrantTypeDao) {
        this.oauthClientGrantTypeDao = oauthClientGrantTypeDao;
    }

    /**
     * insert oauth client grant type
     * 
     * @param oauthClientGrantType
     *            oauth client grant type
     * @return insert count
     */
    public int insert(final OauthClientGrantType oauthClientGrantType) {
        return oauthClientGrantTypeDao.insert(oauthClientGrantType);
    }
}
