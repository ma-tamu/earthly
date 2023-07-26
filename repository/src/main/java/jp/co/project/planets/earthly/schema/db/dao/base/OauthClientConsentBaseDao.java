package jp.co.project.planets.earthly.schema.db.dao.base;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.project.planets.earthly.schema.db.entity.OauthClientConsent;

/**
 */
@Dao
@ConfigAutowireable
public interface OauthClientConsentBaseDao {

    /**
     * @param id
     * @return the OauthClientConsent entity
     */
    @Select
    OauthClientConsent selectById(String id);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(OauthClientConsent entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(OauthClientConsent entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(OauthClientConsent entity);
}
