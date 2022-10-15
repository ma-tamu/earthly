package jp.co.project.planets.earthly.db.dao;

import jp.co.project.planets.earthly.db.dao.base.Oauth2AuthorizationBaseDao;
import org.seasar.doma.Dao;
import org.seasar.doma.boot.ConfigAutowireable;

/**
 * oauth2 authorization dao
 */
@Dao
@ConfigAutowireable
public interface OAuth2AuthorizationDao extends Oauth2AuthorizationBaseDao {

//    @Select
//    Oauth2Authorization selectByState(String state);
//
//    @Select
//    Oauth2Authorization selectByCode(String code);
//
//    @Select
//    Oauth2Authorization selectByAccessToken(String accessToken);
//
//    @Select
//    Oauth2Authorization selectByRefreshToken(String refreshToken);
//
//    @Select
//    Oauth2Authorization selectByUnknownToken(String token);
}
