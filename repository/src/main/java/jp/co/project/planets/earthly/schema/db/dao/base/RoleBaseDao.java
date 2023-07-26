package jp.co.project.planets.earthly.schema.db.dao.base;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.project.planets.earthly.schema.db.entity.Role;

/**
 */
@Dao
@ConfigAutowireable
public interface RoleBaseDao {

    /**
     * @param id
     * @return the Role entity
     */
    @Select
    Role selectById(String id);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(Role entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(Role entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(Role entity);
}
