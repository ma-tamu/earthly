package jp.co.project.planets.earthly.schema.repository;

import java.util.List;

import org.seasar.doma.jdbc.criteria.QueryDsl;
import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.UserRoleDao;
import jp.co.project.planets.earthly.schema.db.entity.UserRole;
import jp.co.project.planets.earthly.schema.db.entity.UserRole_;

/**
 * user role repository
 */
@Repository
public class UserRoleRepository {

    private final UserRoleDao userRoleDao;
    private final QueryDsl queryDsl;

    /**
     * new instance user role repository
     * 
     * @param userRoleDao
     *            user role dao
     */
    public UserRoleRepository(final UserRoleDao userRoleDao, final QueryDsl queryDsl) {
        this.userRoleDao = userRoleDao;
        this.queryDsl = queryDsl;
    }

    /**
     * ユーザーに紐づいているロールを取得
     *
     * @param userId
     *            ユーザーID
     * @param roleIds
     *            取得対象のロールID
     * @return ユーザーに紐づくロールリスト
     */
    public List<UserRole> findByUserIdAndRoleId(final String userId, final List<String> roleIds) {
        return userRoleDao.selectByUserIdAndRoleId(userId, roleIds);
    }

    /**
     * insert user role
     * 
     * @param userRole
     *            user role
     * @return insert count
     */
    public int insert(final UserRole userRole) {
        return userRoleDao.insert(userRole);
    }

    /**
     * delete user role
     * 
     * @param userRole
     *            user role
     * @return delete count
     */
    public int delete(final UserRole userRole) {
        return userRoleDao.delete(userRole);
    }

    public int deleteByRoleId(final String roleId) {
        final var criteria = new UserRole_();
        return queryDsl.delete(criteria).where(w -> w.eq(criteria.roleId, roleId)).execute();
    }

    public int deleteByRoleIdAndUserId(final String roleId, final List<String> userIdList) {
        final var criteria = new UserRole_();
        return queryDsl.delete(criteria).where(w -> {
            w.eq(criteria.roleId, roleId);
            w.in(criteria.userId, userIdList);
        }).execute();
    }
}
