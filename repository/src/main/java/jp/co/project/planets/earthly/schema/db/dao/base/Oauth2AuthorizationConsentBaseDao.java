package jp.co.project.planets.earthly.schema.db.dao.base;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.project.planets.earthly.schema.db.entity.Oauth2AuthorizationConsent;

/**
 */
@Dao
@ConfigAutowireable
public interface Oauth2AuthorizationConsentBaseDao {

    /**
     * @param id
     * @return the Oauth2AuthorizationConsent entity
     */
    @Select
    Oauth2AuthorizationConsent selectById(String id);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(Oauth2AuthorizationConsent entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(Oauth2AuthorizationConsent entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(Oauth2AuthorizationConsent entity);
}
