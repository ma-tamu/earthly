package jp.co.project.planets.earthly.repository;

import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.db.dao.UserRoleDao;
import jp.co.project.planets.earthly.db.entity.UserRole;

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
