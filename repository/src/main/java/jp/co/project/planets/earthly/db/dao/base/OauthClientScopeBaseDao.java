package jp.co.project.planets.earthly.db.dao.base;

import jp.co.project.planets.earthly.db.entity.OauthClientScope;
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
