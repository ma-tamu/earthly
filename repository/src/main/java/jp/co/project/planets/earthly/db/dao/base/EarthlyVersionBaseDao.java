package jp.co.project.planets.earthly.db.dao.base;

import jp.co.project.planets.earthly.db.entity.EarthlyVersion;
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
public interface EarthlyVersionBaseDao {

    /**
     * @param installedRank
     * @return the EarthlyVersion entity
     */
    @Select
    EarthlyVersion selectById(Integer installedRank);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(EarthlyVersion entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(EarthlyVersion entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(EarthlyVersion entity);
}
