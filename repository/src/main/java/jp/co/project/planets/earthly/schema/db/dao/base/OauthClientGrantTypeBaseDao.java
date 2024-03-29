package jp.co.project.planets.earthly.schema.db.dao.base;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.project.planets.earthly.schema.db.entity.OauthClientGrantType;

/**
 */
@Dao
@ConfigAutowireable
public interface OauthClientGrantTypeBaseDao {

    /**
     * @param id
     * @return the OauthClientGrantType entity
     */
    @Select
    OauthClientGrantType selectById(String id);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(OauthClientGrantType entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(OauthClientGrantType entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(OauthClientGrantType entity);
}
