package jp.co.project.planets.earthly.schema.db.dao.base;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.project.planets.earthly.schema.db.entity.GrantType;

/**
 */
@Dao
@ConfigAutowireable
public interface GrantTypeBaseDao {

    /**
     * @param id
     * @return the GrantType entity
     */
    @Select
    GrantType selectById(String id);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(GrantType entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(GrantType entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(GrantType entity);
}
