package jp.co.project.planets.earthly.db.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;

import jp.co.project.planets.earthly.db.annotation.ConfigAutowireable;
import jp.co.project.planets.earthly.db.dao.base.UserRoleBaseDao;
import jp.co.project.planets.earthly.db.entity.UserRole;

/**
 * user role dao
 */
@Dao
@ConfigAutowireable
public interface UserRoleDao extends UserRoleBaseDao {

    /**
     * ユーザーに紐づいているロールを取得
     * 
     * @param userId
     *            ユーザーID
     * @param roleIds
     *            取得対象のロールID
     * @return ユーザーに紐づくロールリスト
     */
    @Select
    List<UserRole> selectByUserIdAndRoleId(String userId, List<String> roleIds);
}
