package jp.co.project.planets.earthly.webapp.service;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.project.planets.earthly.common.logic.RoleLogic;
import jp.co.project.planets.earthly.core.account.Account;
import jp.co.project.planets.earthly.schema.db.entity.Permission;
import jp.co.project.planets.earthly.schema.db.entity.Role;
import jp.co.project.planets.earthly.schema.db.entity.RolePermission;
import jp.co.project.planets.earthly.schema.repository.PermissionRepository;
import jp.co.project.planets.earthly.schema.repository.RolePermissionRepository;
import jp.co.project.planets.earthly.schema.repository.RoleRepository;
import jp.co.project.planets.earthly.webapp.emuns.ErrorCode;
import jp.co.project.planets.earthly.webapp.exception.BadRequestException;
import jp.co.project.planets.earthly.webapp.exception.ForbiddenException;
import jp.co.project.planets.earthly.webapp.exception.NotFoundException;
import jp.co.project.planets.earthly.webapp.model.dto.PermissionDetailDto;

@Service
public class PermissionService {

    private final RoleLogic roleLogic;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;

    public PermissionService(final RoleLogic roleLogic, final PermissionRepository permissionRepository,
        final RoleRepository roleRepository, final RolePermissionRepository rolePermissionRepository) {
        this.roleLogic = roleLogic;
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }

    @Transactional
    public Page<Permission> search(final String name, final Pageable pageable, final Account account) {

        if (!roleLogic.hasViewRole(account)) {
            throw new ForbiddenException(ErrorCode.EWA4XX040);
        }

        final var permissionSearchResultDto = permissionRepository.findByName(name, pageable);
        return new PageImpl<>(permissionSearchResultDto.permissionList(), pageable, permissionSearchResultDto.total());
    }

    @Transactional
    public PermissionDetailDto getDetails(final String id, final Account account) {
        if (!roleLogic.hasViewRole(account)) {
            throw new ForbiddenException(ErrorCode.EWA4XX040);
        }

        final var permission = permissionRepository.findByPrimaryKey(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EWA4XX041));

        final var pageRequest = PageRequest.of(0, 10);
        final var assignedRoles = roleRepository.findAssignedRoleByPermissionId(id, null, pageRequest, account);
        final var assignedRolePage = new PageImpl<>(assignedRoles.roleList(), pageRequest, assignedRoles.total());

        final var unassignedRoles = roleRepository.findUnassignedRoleByPermissionId(id, null, pageRequest, account);
        final var unassignedRolePage = new PageImpl<>(unassignedRoles.roleList(), pageRequest, unassignedRoles.total());

        return new PermissionDetailDto(permission, assignedRolePage, unassignedRolePage);
    }

    @Transactional
    public Page<Role> searchAssignedRole(final String id, final String roleName, final Pageable pageable,
        final Account account) {
        if (!roleLogic.hasViewRole(account)) {
            throw new ForbiddenException(ErrorCode.EWA4XX040);
        }
        final var assignedRoles = roleRepository.findAssignedRoleByPermissionId(id, roleName, pageable, account);
        return new PageImpl<>(assignedRoles.roleList(), pageable, assignedRoles.total());
    }

    @Transactional
    public void unassign(final String id, final List<String> roleIds, final Account account) {

        if (!roleLogic.hasEditRole(account)) {
            throw new ForbiddenException(ErrorCode.EWA4XX034);
        }

        final var roles = roleRepository.findAccessibleByPrimaryKeys(roleIds, account);
        if (roles.size() != roleIds.size()) {
            throw new ForbiddenException(ErrorCode.EWA4XX042);
        }

        rolePermissionRepository.deleteByRoleIdsAndPermissionId(roleIds, id);
    }

    @Transactional
    public Page<Role> searchUnassignedRole(final String id, final String roleName, final Pageable pageable,
        final Account account) {
        final var unassignedRoles = roleRepository.findUnassignedRoleByPermissionId(id, roleName, pageable, account);
        return new PageImpl<>(unassignedRoles.roleList(), pageable, unassignedRoles.total());
    }

    @Transactional
    public void assign(final String id, final List<String> roleIds, final Account account) {

        if (!roleLogic.hasEditRole(account)) {
            throw new ForbiddenException(ErrorCode.EWA4XX034);
        }

        final var roles = roleRepository.findAccessibleByPrimaryKeys(roleIds, account);
        if (roles.size() != roleIds.size()) {
            throw new ForbiddenException(ErrorCode.EWA4XX043);
        }

        final var rolePermissions = rolePermissionRepository.findByRoleIdsAndPermissionId(roleIds, id);
        if (CollectionUtils.isNotEmpty(rolePermissions)) {
            throw new BadRequestException(ErrorCode.EWA4XX044);
        }

        for (final var roleId : roleIds) {
            final var rolePermission = new RolePermission(null, roleId, id, null, account.id());
            rolePermissionRepository.insert(rolePermission);
        }
    }
}
