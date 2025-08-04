package jp.co.project.planets.earthly.schema.db.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.project.planets.earthly.schema.db.dao.base.RolePermissionBaseDao;

/**
 * Data Access Object (DAO) interface for managing RolePermission entities.
 * This interface provides methods to perform database operations on
 * RolePermission entities
 * by extending the functionality provided by RolePermissionBaseDao.
 * It is annotated with @Dao and @ConfigAutowireable, enabling integration with
 * the Doma framework
 * for dependency injection and DAO management.
 */
@Dao
@ConfigAutowireable
public interface RolePermissionDao extends RolePermissionBaseDao {
}
