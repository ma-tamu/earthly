package jp.co.project.planets.earthly.repository;

import jp.co.project.planets.earthly.db.dao.PermissionDao;
import jp.co.project.planets.earthly.db.entity.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * permission repository
 */
@Repository
public class PermissionRepository {

    private final PermissionDao permissionDao;

    public PermissionRepository(final PermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }

    /**
     * find by grant permission by user id
     *
     * @param userId
     *         user id
     * @return permission list
     */
    public List<Permission> findGrantPermissionByUserId(final String userId) {
        return permissionDao.selectGrantPermissionByUserId(userId);
    }
}
