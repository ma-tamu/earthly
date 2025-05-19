package jp.co.project.planets.earthly.schema.db.dao;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import jp.co.project.planets.earthly.schema.db.dao.base.UserBaseDao;
import jp.co.project.planets.earthly.schema.db.entity.User;
import jp.co.project.planets.earthly.schema.model.entity.UserSimpleEntity;
import jp.co.project.planets.earthly.schema.strategy.UserAggregateStrategy;

/**
 * user dao
 */
@Dao
@ConfigAutowireable
public interface UserDao extends UserBaseDao {

    @Select
    List<User> selectByPrimaryKeysAccessibly(List<String> ids, boolean hasViewAllCompany, String executionUserId);

    @Select(aggregateStrategy = UserAggregateStrategy.class)
    Optional<jp.co.project.planets.earthly.schema.model.entity.User> selectByLoginId(String loginId);

    @Select(aggregateStrategy = UserAggregateStrategy.class)
    Optional<jp.co.project.planets.earthly.schema.model.entity.User> selectAccessibleByPrimaryKey(String id,
        boolean hasViewAllCompany, String executionUserId);

    @Select
    List<UserSimpleEntity> selectByLoginIdAndNameAndCompany(String loginId, String name, String company,
        boolean hasViewAllCompany, String executionUserId, SelectOptions selectOptions);

    @Select(aggregateStrategy = UserAggregateStrategy.class)
    List<jp.co.project.planets.earthly.schema.model.entity.User> selectCompanyManagerByName(String loginId, String name,
        String companyId, String companyName, boolean hasViewAllUser, String executionUserId,
        SelectOptions selectOptions);

    @Select(aggregateStrategy = UserAggregateStrategy.class)
    List<jp.co.project.planets.earthly.schema.model.entity.User> selectNotCompanyManagerByNameAndCompanyName(
        String loginId, String name, String companyName, String companyId, boolean hasViewAllUser,
        String executionUserId, SelectOptions selectOptions);

    @Select(aggregateStrategy = UserAggregateStrategy.class)
    List<jp.co.project.planets.earthly.schema.model.entity.User> selectBelongOrganizationByLikeLoginIdAndName(
        String loginId, String name, String companyId, String organizationId, boolean hasViewAllUser,
        String executionUserId,
        SelectOptions selectOptions);

    @Select(aggregateStrategy = UserAggregateStrategy.class)
    List<jp.co.project.planets.earthly.schema.model.entity.User> selectNotBelongOrganizationByLikeLoginIdAndName(
        String loginId, String name, String companyId, String organizationId, boolean hasViewAllUser,
        String executionUserId, SelectOptions selectOptions);
}
