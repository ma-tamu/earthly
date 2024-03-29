package jp.co.project.planets.earthly.schema.db.dao.base;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.project.planets.earthly.schema.db.entity.OauthClientRedirectUrl;

/**
 */
@Dao
@ConfigAutowireable
public interface OauthClientRedirectUrlBaseDao {

    /**
     * @param id
     * @return the OauthClientRedirectUrl entity
     */
    @Select
    OauthClientRedirectUrl selectById(String id);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(OauthClientRedirectUrl entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(OauthClientRedirectUrl entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(OauthClientRedirectUrl entity);
}
