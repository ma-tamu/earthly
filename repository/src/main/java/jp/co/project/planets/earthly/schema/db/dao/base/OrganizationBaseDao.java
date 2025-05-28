package jp.co.project.planets.earthly.schema.db.dao.base;

import jp.co.project.planets.earthly.schema.db.entity.Organization;
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
public interface OrganizationBaseDao {

    /**
     * @param id
     * @return the Organization entity
     */
    @Select
    Organization selectById(String id);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert(excludeNull = true)
    int insert(Organization entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update(excludeNull = true)
    int update(Organization entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(Organization entity);
}
