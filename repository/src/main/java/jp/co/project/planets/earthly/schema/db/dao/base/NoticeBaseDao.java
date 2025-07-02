package jp.co.project.planets.earthly.schema.db.dao.base;

import jp.co.project.planets.earthly.schema.db.entity.Notice;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

/**
 */
@Dao
@ConfigAutowireable
public interface NoticeBaseDao {

    /**
     * @param id
     * @return the Notice entity
     */
    @Select
    Notice selectById(String id);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert(excludeNull = true)
    int insert(Notice entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update(excludeNull = true)
    int update(Notice entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(Notice entity);
}
