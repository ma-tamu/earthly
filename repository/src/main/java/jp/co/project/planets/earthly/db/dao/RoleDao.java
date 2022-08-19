package jp.co.project.planets.earthly.db.dao;

import jp.co.project.planets.earthly.db.dao.base.RoleBaseDao;
import jp.co.project.planets.earthly.db.entity.Role;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * role dao
 */
@Dao
@ConfigAutowireable
public interface RoleDao extends RoleBaseDao {

    @Select
    List<Role> selectGrantedRoleByUserId(String userId, boolean hasViewAllRole, String executionUserId);
}
