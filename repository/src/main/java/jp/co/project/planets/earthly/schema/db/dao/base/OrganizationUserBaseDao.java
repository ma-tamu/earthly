package jp.co.project.planets.earthly.schema.db.dao.base;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.project.planets.earthly.schema.db.entity.OrganizationUser;

/**
 */
@Dao
@ConfigAutowireable
public interface OrganizationUserBaseDao {

    /**
     * @param id
     * @return the OrganizationUser entity
     */
    @Select
    OrganizationUser selectById(String id);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert(excludeNull = true)
    int insert(OrganizationUser entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update(excludeNull = true)
    int update(OrganizationUser entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(OrganizationUser entity);
}
