package jp.co.project.planets.earthly.db.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;

import jp.co.project.planets.earthly.db.annotation.ConfigAutowireable;
import jp.co.project.planets.earthly.db.dao.base.ScopeBaseDao;
import jp.co.project.planets.earthly.db.entity.Scope;

/**
 * scope dao
 */
@Dao
@ConfigAutowireable
public interface ScopeDao extends ScopeBaseDao {

    @Select
    List<Scope> selectByClientId(String clientId);
}
