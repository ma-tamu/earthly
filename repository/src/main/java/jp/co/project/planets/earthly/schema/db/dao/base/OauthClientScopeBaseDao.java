package jp.co.project.planets.earthly.schema.db.dao.base;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.project.planets.earthly.schema.db.entity.OauthClientScope;

/**
 */
@Dao
@ConfigAutowireable
public interface OauthClientScopeBaseDao {

    /**
     * @param id
     * @return the OauthClientScope entity
     */
    @Select
    OauthClientScope selectById(String id);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(OauthClientScope entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(OauthClientScope entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(OauthClientScope entity);
}
