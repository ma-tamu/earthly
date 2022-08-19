package jp.co.project.planets.earthly.db.dao;

import jp.co.project.planets.earthly.db.dao.base.CompanyBaseDao;
import jp.co.project.planets.earthly.model.entity.CompanyEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.Optional;

/**
 * company dao
 */
@Dao
@ConfigAutowireable
public interface CompanyDao extends CompanyBaseDao {

    @Select
    Optional<CompanyEntity> selectByPrimaryKey(String id);
}
