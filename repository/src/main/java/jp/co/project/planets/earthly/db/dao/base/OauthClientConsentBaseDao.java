package jp.co.project.planets.earthly.db.dao.base;

import jp.co.project.planets.earthly.db.entity.OauthClientConsent;
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
public interface OauthClientConsentBaseDao {

    /**
     * @param registeredClientId
     * @param principalName
     * @return the OauthClientConsent entity
     */
    @Select
    OauthClientConsent selectById(String registeredClientId, String principalName);

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
