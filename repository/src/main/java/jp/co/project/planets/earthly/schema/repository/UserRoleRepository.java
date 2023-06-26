package jp.co.project.planets.earthly.schema.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.UserRoleDao;
import jp.co.project.planets.earthly.schema.db.entity.UserRole;

/**
 * user role repository
 */
@Repository
public class UserRoleRepository {

    private final UserRoleDao userRoleDao;

    /**
     * new instance user role repository
     * 
     * @param userRoleDao
     *            user role dao
     */
    public UserRoleRepository(final UserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
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
}
