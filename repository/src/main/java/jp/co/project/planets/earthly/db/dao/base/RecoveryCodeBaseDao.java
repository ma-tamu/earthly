package jp.co.project.planets.earthly.db.dao.base;

import jp.co.project.planets.earthly.db.entity.RecoveryCode;
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
public interface RecoveryCodeBaseDao {

    /**
     * @param id
     * @return the RecoveryCode entity
     */
    @Select
    RecoveryCode selectById(String id);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(RecoveryCode entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(RecoveryCode entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(RecoveryCode entity);
}
