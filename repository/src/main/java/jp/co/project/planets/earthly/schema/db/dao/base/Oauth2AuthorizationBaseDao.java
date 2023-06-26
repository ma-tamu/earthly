package jp.co.project.planets.earthly.schema.db.dao.base;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

import jp.co.project.planets.earthly.schema.config.DomaConfig;
import jp.co.project.planets.earthly.schema.db.annotation.ConfigAutowireable;
import jp.co.project.planets.earthly.schema.db.entity.Oauth2Authorization;

/**
 */
@Dao(config = DomaConfig.class)
@ConfigAutowireable
public interface Oauth2AuthorizationBaseDao {

    /**
     * @param id
     * @return the Oauth2Authorization entity
     */
    @Select
    Oauth2Authorization selectById(String id);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(Oauth2Authorization entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(Oauth2Authorization entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(Oauth2Authorization entity);
}
