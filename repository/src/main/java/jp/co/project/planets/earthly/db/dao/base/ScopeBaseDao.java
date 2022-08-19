package jp.co.project.planets.earthly.db.dao.base;

import jp.co.project.planets.earthly.db.entity.Scope;
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
public interface ScopeBaseDao {

    /**
     * @param id
     * @return the Scope entity
     */
    @Select
    Scope selectById(String id);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(Scope entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(Scope entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(Scope entity);
}
