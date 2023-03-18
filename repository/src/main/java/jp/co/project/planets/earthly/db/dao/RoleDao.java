package jp.co.project.planets.earthly.db.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import jp.co.project.planets.earthly.db.dao.base.RoleBaseDao;
import jp.co.project.planets.earthly.db.entity.Role;

/**
 * role dao
 */
@Dao
@ConfigAutowireable
public interface RoleDao extends RoleBaseDao {

    @Select
    List<Role> selectGrantedRoleByUserId(String userId, boolean hasViewAllRole, String executionUserId);

    @Select
    List<Role> selectUnassignedRoleByUserIdAndLikeName(String userId, String name,
            boolean hasViewAllRole, String executionUserId, SelectOptions options);
}
