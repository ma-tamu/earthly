package jp.co.project.planets.earthly.schema.repository;

import org.seasar.doma.jdbc.criteria.Entityql;
import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.OAuth2AuthorizationDao;
import jp.co.project.planets.earthly.schema.db.entity.Oauth2Authorization;
import jp.co.project.planets.earthly.schema.db.entity.Oauth2Authorization_;

/**
 * oauth2 authorization repository
 */
@Repository
public class OAuth2AuthorizationRepository {

    private final OAuth2AuthorizationDao oauth2AuthorizationDao;
    private final Entityql entityql;

    public OAuth2AuthorizationRepository(final OAuth2AuthorizationDao oauth2AuthorizationDao, final Entityql entityql) {
        this.oauth2AuthorizationDao = oauth2AuthorizationDao;
        this.entityql = entityql;
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
        final var oauth2Authorization = new Oauth2Authorization_();
        return entityql.from(oauth2Authorization).where(w -> w.eq(oauth2Authorization.state, state)).fetchOne();
    }

    /**
     * find by auth code
     *
     * @param code
     *            auth code
     * @return Oauth2Authorization
     */
    public Oauth2Authorization findByCode(final String code) {
        final var oauth2Authorization = new Oauth2Authorization_();
        return entityql.from(oauth2Authorization).where(w -> w.eq(oauth2Authorization.authorizationCodeValue, code))
                .fetchOne();
    }

    /**
     * find by access token
     *
     * @param accessToken
     *            access token
     * @return Oauth2Authorization
     */
    public Oauth2Authorization findByAccessToken(final String accessToken) {
        final var oauth2Authorization = new Oauth2Authorization_();
        return entityql.from(oauth2Authorization).where(w -> w.eq(oauth2Authorization.accessTokenValue, accessToken))
                .fetchOne();
    }

    /**
     * find by refresh token
     *
     * @param refreshToken
     *            refresh token
     * @return Oauth2Authorization
     */
    public Oauth2Authorization findByRefreshToken(final String refreshToken) {
        final var oauth2Authorization = new Oauth2Authorization_();
        return entityql.from(oauth2Authorization).where(w -> w.eq(oauth2Authorization.refreshTokenValue, refreshToken))
                .fetchOne();
    }

    /**
     * find by unknown token
     *
     * @param token
     *            unknown token
     * @return Oauth2Authorization
     */
    public Oauth2Authorization findByUnknownToken(final String token) {
        final var oauth2Authorization = new Oauth2Authorization_();
        return entityql.from(oauth2Authorization)
                .where(w -> w.or(() -> {
                    w.eq(oauth2Authorization.accessTokenValue, token);
                    w.eq(oauth2Authorization.accessTokenValue, token);
                    w.eq(oauth2Authorization.refreshTokenValue, token);
                })).fetchOne();
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
