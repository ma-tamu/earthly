package jp.co.project.planets.earthly.schema.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.PermissionDao;
import jp.co.project.planets.earthly.schema.db.entity.Permission;

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
     *            user id
     * @return permission list
     */
    public List<Permission> findGrantPermissionByUserId(final String userId) {
        return permissionDao.selectGrantPermissionByUserId(userId);
    }
}
