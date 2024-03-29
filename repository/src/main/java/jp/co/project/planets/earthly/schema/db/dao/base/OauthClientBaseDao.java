package jp.co.project.planets.earthly.schema.db.dao.base;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.project.planets.earthly.schema.db.entity.OauthClient;

/**
 */
@Dao
@ConfigAutowireable
public interface OauthClientBaseDao {

    /**
     * @param id
     * @return the OauthClient entity
     */
    @Select
    OauthClient selectById(String id);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(OauthClient entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(OauthClient entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(OauthClient entity);
}
