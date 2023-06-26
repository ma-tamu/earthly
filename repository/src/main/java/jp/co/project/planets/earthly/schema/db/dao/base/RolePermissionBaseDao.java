package jp.co.project.planets.earthly.schema.db.dao.base;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

import jp.co.project.planets.earthly.schema.config.DomaConfig;
import jp.co.project.planets.earthly.schema.db.annotation.ConfigAutowireable;
import jp.co.project.planets.earthly.schema.db.entity.RolePermission;

/**
 */
@Dao(config = DomaConfig.class)
@ConfigAutowireable
public interface RolePermissionBaseDao {

    /**
     * @param id
     * @return the RolePermission entity
     */
    @Select
    RolePermission selectById(String id);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(RolePermission entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(RolePermission entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(RolePermission entity);
}
