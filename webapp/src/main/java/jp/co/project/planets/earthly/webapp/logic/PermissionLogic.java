package jp.co.project.planets.earthly.webapp.logic;

import java.util.List;

import org.springframework.stereotype.Component;

import jp.co.project.planets.earthly.schema.emuns.PermissionEnum;
import jp.co.project.planets.earthly.schema.repository.PermissionRepository;

/**
 * permission logic
 */
@Component
public class PermissionLogic {

    private final PermissionRepository permissionRepository;

    /**
     * new instance permission logic
     *
     * @param permissionRepository
     *            permission repository
     */
    public PermissionLogic(final PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    /**
     * find permission enum list by user id
     *
     * @param userId
     *            user id
     * @return permission enum list
     */
    public List<PermissionEnum> findPermissionEnumListByUserId(final String userId) {
        return permissionRepository.findGrantPermissionByUserId(userId).stream().map(
                it -> PermissionEnum.of(it.getId())).toList();
    }
}
