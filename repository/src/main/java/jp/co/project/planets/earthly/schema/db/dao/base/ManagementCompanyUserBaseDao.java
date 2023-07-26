package jp.co.project.planets.earthly.schema.db.dao.base;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.project.planets.earthly.schema.db.entity.ManagementCompanyUser;

/**
 */
@Dao
@ConfigAutowireable
public interface ManagementCompanyUserBaseDao {

    /**
     * @param id
     * @return the ManagementCompanyUser entity
     */
    @Select
    ManagementCompanyUser selectById(String id);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(ManagementCompanyUser entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(ManagementCompanyUser entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(ManagementCompanyUser entity);
}
