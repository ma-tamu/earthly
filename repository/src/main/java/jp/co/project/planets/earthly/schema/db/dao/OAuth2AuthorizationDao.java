package jp.co.project.planets.earthly.schema.db.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.project.planets.earthly.schema.db.dao.base.Oauth2AuthorizationBaseDao;

/**
 * oauth2 authorization dao
 */
@Dao
@ConfigAutowireable
public interface OAuth2AuthorizationDao extends Oauth2AuthorizationBaseDao {
}
