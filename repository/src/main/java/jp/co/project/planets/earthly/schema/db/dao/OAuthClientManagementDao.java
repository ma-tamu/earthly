package jp.co.project.planets.earthly.schema.db.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.project.planets.earthly.schema.db.dao.base.OauthClientManagementBaseDao;

/**
 * oauth client management dao
 */
@Dao
@ConfigAutowireable
public interface OAuthClientManagementDao extends OauthClientManagementBaseDao {
}
