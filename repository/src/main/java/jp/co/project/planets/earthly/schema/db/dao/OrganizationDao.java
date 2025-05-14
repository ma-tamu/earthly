package jp.co.project.planets.earthly.schema.db.dao;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Returning;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;
import org.seasar.doma.jdbc.query.DuplicateKeyType;

import jp.co.project.planets.earthly.schema.db.dao.base.OrganizationBaseDao;
import jp.co.project.planets.earthly.schema.db.entity.Organization;
import jp.co.project.planets.earthly.schema.strategy.OrganizationAggregateStrategy;

/**
 * 組織DAO
 */
@Dao
@ConfigAutowireable
public interface OrganizationDao extends OrganizationBaseDao {

    @Insert(excludeNull = true, returning = @Returning, duplicateKeyType = DuplicateKeyType.IGNORE)
    Optional<Organization> insertReturning(Organization organization);

    @Select(aggregateStrategy = OrganizationAggregateStrategy.class)
    Optional<jp.co.project.planets.earthly.schema.model.entity.Organization> selectAccessibleByPrimaryKey(String id,
        boolean hasViewAllCompany, String operatorUserId);

    @Select(aggregateStrategy = OrganizationAggregateStrategy.class)
    List<jp.co.project.planets.earthly.schema.model.entity.Organization> selectByCompanyIdAndLikeName(String companyId,
        String name, SelectOptions selectOptions);
}
