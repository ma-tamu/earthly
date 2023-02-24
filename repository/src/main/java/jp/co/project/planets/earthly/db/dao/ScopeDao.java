package jp.co.project.planets.earthly.db.dao;

import jp.co.project.planets.earthly.db.dao.base.ScopeBaseDao;
import jp.co.project.planets.earthly.db.entity.Scope;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * scope dao
 */
@Dao
@ConfigAutowireable
public interface ScopeDao extends ScopeBaseDao {

    @Select
    List<Scope> selectByClientId(String clientId);
}
