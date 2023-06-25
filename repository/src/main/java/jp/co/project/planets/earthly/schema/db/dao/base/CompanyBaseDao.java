package jp.co.project.planets.earthly.schema.db.dao.base;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

import jp.co.project.planets.earthly.schema.config.DomaConfig;
import jp.co.project.planets.earthly.schema.db.annotation.ConfigAutowireable;
import jp.co.project.planets.earthly.schema.db.entity.Company;

/**
 */
@Dao(config = DomaConfig.class)
@ConfigAutowireable
public interface CompanyBaseDao {

    /**
     * @param id
     * @return the Company entity
     */
    @Select
    Company selectById(String id);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(Company entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(Company entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(Company entity);
}
