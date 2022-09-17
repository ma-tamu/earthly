package jp.co.project.planets.earthly.db.dao.base;

import jp.co.project.planets.earthly.db.entity.ManagementCompanyUser;
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
public interface ManagementCompanyUserBaseDao {

    /**
     * @param companyId
     * @param userId
     * @return the ManagementCompanyUser entity
     */
    @Select
    ManagementCompanyUser selectById(String companyId, String userId);

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
