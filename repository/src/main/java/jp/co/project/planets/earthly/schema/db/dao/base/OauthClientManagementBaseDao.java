package jp.co.project.planets.earthly.schema.db.dao.base;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.project.planets.earthly.schema.db.entity.OauthClientManagement;

/**
 *
 */
@Dao
@ConfigAutowireable
public interface OauthClientManagementBaseDao {

    /**
     * @param id
     * @return the OauthClientManagement entity
     */
    @Select
    OauthClientManagement selectById(String id);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(OauthClientManagement entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(OauthClientManagement entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(OauthClientManagement entity);
}
