package jp.co.project.planets.earthly.schema.db.dao.base;

import org.seasar.doma.*;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.project.planets.earthly.schema.db.entity.OpenNotice;

/**
 */
@Dao
@ConfigAutowireable
public interface OpenNoticeBaseDao {

    /**
     * @param id
     * @return the OpenNotice entity
     */
    @Select
    OpenNotice selectById(String id);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert(excludeNull = true)
    int insert(OpenNotice entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update(excludeNull = true)
    int update(OpenNotice entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(OpenNotice entity);
}
