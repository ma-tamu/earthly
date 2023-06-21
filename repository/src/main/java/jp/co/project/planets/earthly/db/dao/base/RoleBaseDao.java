package jp.co.project.planets.earthly.db.dao.base;

import jp.co.project.planets.earthly.config.DomaConfig;
import jp.co.project.planets.earthly.db.entity.Role;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import jp.co.project.planets.earthly.db.annotation.ConfigAutowireable;

/**
 */
@Dao(config = DomaConfig.class)
@ConfigAutowireable
public interface RoleBaseDao {

    /**
     * @param id
     * @return the Role entity
     */
    @Select
    Role selectById(String id);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(Role entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(Role entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(Role entity);
}
