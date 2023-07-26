package jp.co.project.planets.earthly.schema.db.dao.base;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.project.planets.earthly.schema.db.entity.PasswordToken;

/**
 */
@Dao
@ConfigAutowireable
public interface PasswordTokenBaseDao {

    /**
     * @param id
     * @return the PasswordToken entity
     */
    @Select
    PasswordToken selectById(String id);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(PasswordToken entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(PasswordToken entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(PasswordToken entity);
}
