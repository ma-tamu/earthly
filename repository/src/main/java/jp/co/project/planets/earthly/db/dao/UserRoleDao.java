package jp.co.project.planets.earthly.db.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.project.planets.earthly.db.dao.base.UserRoleBaseDao;

/**
 * user role dao
 */
@Dao
@ConfigAutowireable
public interface UserRoleDao extends UserRoleBaseDao {
}
