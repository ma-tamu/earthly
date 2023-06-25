package jp.co.project.planets.earthly.schema.repository;

import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.OAuth2AuthorizationDao;
import jp.co.project.planets.earthly.schema.db.entity.Oauth2Authorization;

/**
 * oauth2 authorization repository
 */
@Repository
public class OAuth2AuthorizationRepository {

    private final OAuth2AuthorizationDao oauth2AuthorizationDao;

    public OAuth2AuthorizationRepository(final OAuth2AuthorizationDao oauth2AuthorizationDao) {
        this.oauth2AuthorizationDao = oauth2AuthorizationDao;
    }

    /**
     * find by primary key
     *
     * @param id
     *            id
     * @return Oauth2Authorization
     */
    public Oauth2Authorization findByPrimaryKey(final String id) {
        return oauth2AuthorizationDao.selectById(id);
    }

    /**
     * find by state
     *
     * @param state
     *            state
     * @return Oauth2Authorization
     */
    public Oauth2Authorization findByState(final String state) {
        return oauth2AuthorizationDao.selectByState(state);
    }

    /**
     * find by auth code
     *
     * @param code
     *            auth code
     * @return Oauth2Authorization
     */
    public Oauth2Authorization findByCode(final String code) {
        return oauth2AuthorizationDao.selectByCode(code);
    }

    /**
     * find by access token
     *
     * @param accessToken
     *            access token
     * @return Oauth2Authorization
     */
    public Oauth2Authorization findByAccessToken(final String accessToken) {
        return oauth2AuthorizationDao.selectByAccessToken(accessToken);
    }

    /**
     * find by refresh token
     *
     * @param refreshToken
     *            refresh token
     * @return Oauth2Authorization
     */
    public Oauth2Authorization findByRefreshToken(final String refreshToken) {
        return oauth2AuthorizationDao.selectByRefreshToken(refreshToken);
    }

    /**
     * find by unknown token
     *
     * @param token
     *            unknown token
     * @return Oauth2Authorization
     */
    public Oauth2Authorization findByUnknownToken(final String token) {
        return oauth2AuthorizationDao.selectByUnknownToken(token);
    }

    public int insert(final Oauth2Authorization oauth2Authorization) {
        return oauth2AuthorizationDao.insert(oauth2Authorization);
    }

    public int update(final Oauth2Authorization updatedOAuth2Authorization) {
        return oauth2AuthorizationDao.update(updatedOAuth2Authorization);
    }

    public int delete(final Oauth2Authorization oauth2Authorization) {
        return oauth2AuthorizationDao.delete(oauth2Authorization);
    }

}
