package jp.co.project.planets.earthly.schema.db.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import jp.co.project.planets.earthly.schema.db.dao.base.PermissionBaseDao;
import jp.co.project.planets.earthly.schema.db.entity.Permission;

/**
 * permission dao
 */
@Dao
@ConfigAutowireable
public interface PermissionDao extends PermissionBaseDao {

    @Select
    List<Permission> selectGrantPermissionByUserId(String userId);

    @Select
    List<Permission> selectRoleAssignedByRoleIdAndAnyName(String roleId, String name, SelectOptions selectOptions);

    @Select
    List<Permission> selectRoleUnassignedByRoleIdAndAnyName(String roleId, String name, SelectOptions selectOptions);
}
