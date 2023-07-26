package jp.co.project.planets.earthly.schema.db.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.project.planets.earthly.schema.db.dao.base.ScopeBaseDao;
import jp.co.project.planets.earthly.schema.db.entity.Scope;

/**
 * scope dao
 */
@Dao
@ConfigAutowireable
public interface ScopeDao extends ScopeBaseDao {

    @Select
    List<Scope> selectByClientId(String clientId);
}
