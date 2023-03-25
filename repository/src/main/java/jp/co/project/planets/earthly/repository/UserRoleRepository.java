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

    public UserRoleRepository(final UserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    public int insert(final UserRole userRole) {
        return userRoleDao.insert(userRole);
    }

    public int delete(final UserRole userRole) {
        return userRoleDao.delete(userRole);
    }
}
