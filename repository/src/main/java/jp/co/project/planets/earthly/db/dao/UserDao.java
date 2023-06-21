package jp.co.project.planets.earthly.db.dao;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.jdbc.SelectOptions;

import jp.co.project.planets.earthly.db.annotation.ConfigAutowireable;
import jp.co.project.planets.earthly.db.dao.base.UserBaseDao;
import jp.co.project.planets.earthly.db.entity.User;
import jp.co.project.planets.earthly.model.entity.UserSimpleEntity;

/**
 * user dao
 */
@Dao
@ConfigAutowireable
public interface UserDao extends UserBaseDao {

    @Select
    Optional<User> selectByLoginId(String loginId);

    @Select
    Optional<User> selectAccessibleByPrimaryKey(String id, boolean hasViewAllCompany, String executionUserId);

    @Select
    List<UserSimpleEntity> selectByLoginIdAndNameAndCompany(String loginId, String name, String company,
            boolean hasViewAllCompany, String executionUserId, SelectOptions selectOptions);
}
