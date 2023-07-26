package jp.co.project.planets.earthly.schema.db.dao.base;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.project.planets.earthly.schema.db.entity.Country;

/**
 */
@Dao
@ConfigAutowireable
public interface CountryBaseDao {

    /**
     * @param id
     * @return the Country entity
     */
    @Select
    Country selectById(String id);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(Country entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(Country entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(Country entity);
}
