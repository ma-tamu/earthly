package jp.co.project.planets.earthly.db.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;

import jp.co.project.planets.earthly.db.annotation.ConfigAutowireable;
import jp.co.project.planets.earthly.db.dao.base.PermissionBaseDao;
import jp.co.project.planets.earthly.db.entity.Permission;

/**
 * permission dao
 */
@Dao
@ConfigAutowireable
public interface PermissionDao extends PermissionBaseDao {

    @Select
    List<Permission> selectGrantPermissionByUserId(String userId);
}
