package jp.co.project.planets.earthly.schema.db.dao.base;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.project.planets.earthly.schema.db.entity.UserRole;

/**
 */
@Dao
@ConfigAutowireable
public interface UserRoleBaseDao {

    /**
     * @param id
     * @return the UserRole entity
     */
    @Select
    UserRole selectById(String id);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(UserRole entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(UserRole entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(UserRole entity);
}
