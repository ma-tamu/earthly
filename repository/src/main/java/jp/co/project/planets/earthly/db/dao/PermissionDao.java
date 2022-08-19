package jp.co.project.planets.earthly.db.dao;

import jp.co.project.planets.earthly.db.dao.base.PermissionBaseDao;
import jp.co.project.planets.earthly.db.entity.Permission;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * permission dao
 */
@Dao
@ConfigAutowireable
public interface PermissionDao extends PermissionBaseDao {

    @Select
    List<Permission> selectGrantPermissionByUserId(String userId);
}
