package jp.co.project.planets.earthly.schema.db.dao.base;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.project.planets.earthly.schema.db.entity.WorkTime;

/**
 */
@Dao
@ConfigAutowireable
public interface WorkTimeBaseDao {

    /**
     * @param id
     * @return the WorkTime entity
     */
    @Select
    WorkTime selectById(String id);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(WorkTime entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(WorkTime entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(WorkTime entity);
}
