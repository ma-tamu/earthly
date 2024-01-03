package jp.co.project.planets.earthly.schema.repository;

import java.util.List;

import org.seasar.doma.jdbc.criteria.Entityql;
import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.ScopeDao;
import jp.co.project.planets.earthly.schema.db.entity.Scope;

/**
 * scope repository
 */
@Repository
public class ScopeRepository {

    private final ScopeDao scopeDao;
    private final Entityql entityql;

    public ScopeRepository(final ScopeDao scopeDao, final Entityql entityql) {
        this.scopeDao = scopeDao;
        this.entityql = entityql;
    }

    /**
     * find by id
     * 
     * @param id
     *            scope id
     * @return scope
     */
    public Scope findById(final String id) {
        return scopeDao.selectById(id);
    }

    /**
     * find by client id
     * 
     * @param clientId
     *            oauth client id
     * @return scopes
     */
    public List<Scope> findByClientId(final String clientId) {
        return scopeDao.selectByClientId(clientId);
    }
}
