package jp.co.project.planets.earthly.db.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;

import jp.co.project.planets.earthly.db.annotation.ConfigAutowireable;
import jp.co.project.planets.earthly.db.dao.base.Oauth2AuthorizationBaseDao;
import jp.co.project.planets.earthly.db.entity.Oauth2Authorization;

/**
 * oauth2 authorization dao
 */
@Dao
@ConfigAutowireable
public interface OAuth2AuthorizationDao extends Oauth2AuthorizationBaseDao {

    @Select
    Oauth2Authorization selectByState(String state);

    @Select
    Oauth2Authorization selectByCode(String code);

    @Select
    Oauth2Authorization selectByAccessToken(String accessToken);

    @Select
    Oauth2Authorization selectByRefreshToken(String refreshToken);

    @Select
    Oauth2Authorization selectByUnknownToken(String token);
}
