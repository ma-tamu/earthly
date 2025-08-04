package jp.co.project.planets.earthly.webapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.constraints.NotEmpty;
import jp.co.project.planets.earthly.core.account.Account;
import jp.co.project.planets.earthly.schema.db.entity.Permission;
import jp.co.project.planets.earthly.schema.db.entity.Role;
import jp.co.project.planets.earthly.schema.db.entity.RolePermission;
import jp.co.project.planets.earthly.schema.db.entity.UserRole;
import jp.co.project.planets.earthly.schema.emuns.PermissionEnum;
import jp.co.project.planets.earthly.schema.model.entity.User;
import jp.co.project.planets.earthly.schema.repository.PermissionRepository;
import jp.co.project.planets.earthly.schema.repository.RolePermissionRepository;
import jp.co.project.planets.earthly.schema.repository.RoleRepository;
import jp.co.project.planets.earthly.schema.repository.UserRepository;
import jp.co.project.planets.earthly.schema.repository.UserRoleRepository;
import jp.co.project.planets.earthly.webapp.constant.MessageKey;
import jp.co.project.planets.earthly.webapp.emuns.ErrorCode;
import jp.co.project.planets.earthly.webapp.exception.BadRequestException;
import jp.co.project.planets.earthly.webapp.exception.ForbiddenException;
import jp.co.project.planets.earthly.webapp.exception.NotFoundException;
import jp.co.project.planets.earthly.webapp.model.dto.RoleDetailDto;
import jp.co.project.planets.earthly.webapp.model.dto.RoleEditDto;
import jp.co.project.planets.earthly.webapp.model.dto.RoleEntryDto;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final UserRoleRepository userRoleRepository;
    private final RolePermissionRepository rolePermissionRepository;

    private final MessageSource messageSource;

    public RoleService(final RoleRepository roleRepository, final UserRepository userRepository,
        final PermissionRepository permissionRepository, final UserRoleRepository userRoleRepository,
        final RolePermissionRepository rolePermissionRepository, final MessageSource messageSource) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.userRoleRepository = userRoleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.messageSource = messageSource;
    }

    @Transactional
    public Page<Role> search(final String name, final Pageable pageable, final Account account) {
        final var roleSearchResultDto = roleRepository.findByName(name, pageable, account);
        return new PageImpl<>(roleSearchResultDto.roleList(), pageable, roleSearchResultDto.total());
    }

    @Transactional
    public RoleDetailDto getDetail(final String id, final Account account) {
        final var role = roleRepository.findAccessibleByPrimaryKey(id, account)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EWA4XX032));

        final var pageable = PageRequest.of(0, 10);
        final var userSearchResultDto = userRepository
                .findGrantedRoleAccessibleByRoleIdAndAnyLoginIdAndNameAndCompanyName(id,
                        null, null, null, pageable, account);
        final var grantedUserPage = new PageImpl<>(userSearchResultDto.userList(), pageable,
                userSearchResultDto.total());

        final var notGrantedRoleUserSearchResultDto = userRepository
                .findNotGrantedRoleAccessibleByRoleIdAndAnyLoginIdAndNameAndCompanyName(id,
                        null, null, null, pageable, account);
        final var notGrantedUserPage = new PageImpl<>(notGrantedRoleUserSearchResultDto.userList(), pageable,
                notGrantedRoleUserSearchResultDto.total());

        final var assignedPermissionSearchResultDto = permissionRepository.findRoleAssignedByRoleIdAndAnyName(id, null,
                pageable, account);
        final var assignedPermissionPage = new PageImpl<>(assignedPermissionSearchResultDto.permissionList(), pageable,
                assignedPermissionSearchResultDto.total());

        final var unassigendPermissionSearchResultDto = permissionRepository.findRoleUnassignedByRoleIdAndAnyName(id,
                null,
                pageable, account);
        final var unassignedPermissionPage = new PageImpl<>(unassigendPermissionSearchResultDto.permissionList(),
                pageable, unassigendPermissionSearchResultDto.total());

        return new RoleDetailDto(role, grantedUserPage, notGrantedUserPage, assignedPermissionPage,
                unassignedPermissionPage);
    }

    @Transactional
    public void validateEditOperation(final String id, final Account account) {
        roleRepository.findAccessibleByPrimaryKey(id, account)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EWA4XX032));

        if (!account.permissions().contains(PermissionEnum.EDIT_ROLE)) {
            throw new ForbiddenException(ErrorCode.EWA4XX034);
        }
    }

    @Transactional
    public String update(final String id, final RoleEditDto roleEditFormDto, final Account account) {

        validateEditOperation(id, account);

        final var role = new Role(id, roleEditFormDto.name(), roleEditFormDto.description(),
                roleEditFormDto.grantable(), null, null, null, account.id(), null);
        roleRepository.update(role);
        return messageSource.getMessage(MessageKey.UPDATE_SUCCESS, ArrayUtils.EMPTY_OBJECT_ARRAY, Locale.JAPAN);
    }

    @Transactional
    public void delete(final String id, final Account account) {
        validateEditOperation(id, account);

        userRoleRepository.deleteByRoleId(id);
        rolePermissionRepository.deleteByRoleId(id);
        final var role = new Role(id, null, null, null, null, null, null, null, true);
        roleRepository.update(role);
    }

    @Transactional
    public Page<User> searchGrantedUser(final String id, final String loginId, final String name,
        final String companyName, final Pageable pageable, final Account account) {

        roleRepository.findAccessibleByPrimaryKey(id, account)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EWA4XX032));

        final var userPageResultDto = userRepository
                .findGrantedRoleAccessibleByRoleIdAndAnyLoginIdAndNameAndCompanyName(id, loginId, name, companyName,
                        pageable, account);
        return new PageImpl<>(userPageResultDto.userList(), pageable, userPageResultDto.total());
    }

    @Transactional
    public Page<User> searchNotGrantUser(final String id, final String loginId, final String name,
        final String companyName, final Pageable pageable, final Account account) {

        roleRepository.findAccessibleByPrimaryKey(id, account)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EWA4XX032));

        final var userPageResultDto = userRepository
                .findNotGrantedRoleAccessibleByRoleIdAndAnyLoginIdAndNameAndCompanyName(id, loginId, name, companyName,
                        pageable, account);
        return new PageImpl<>(userPageResultDto.userList(), pageable, userPageResultDto.total());
    }

    @Transactional
    public void grant(final String id, final List<String> userIdList, final Account account) {

        validateEditOperation(id, account);
        final var userList = userRepository.findByPrimaryKeysAccessibly(userIdList, account);
        if (userList.size() != userIdList.size()) {
            throw new NotFoundException(ErrorCode.EWA4XX033);
        }
        for (final var userId : userIdList) {
            final var userRole = new UserRole(null, userId, id, LocalDateTime.now(), account.id());
            userRoleRepository.insert(userRole);
        }
    }

    @Transactional
    public void revoke(final String id, final List<String> userIdList, final Account account) {
        validateEditOperation(id, account);
        final var userList = userRepository.findByPrimaryKeysAccessibly(userIdList, account);
        if (userList.size() != userIdList.size()) {
            throw new NotFoundException(ErrorCode.EWA4XX033);
        }
        userRoleRepository.deleteByRoleIdAndUserId(id, userIdList);
    }

    @Transactional
    public Page<Permission> searchAssignedPermission(final String id, final String permissionName,
        final Pageable pageable, final Account account) {

        roleRepository.findAccessibleByPrimaryKey(id, account)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EWA4XX032));

        final var assignedPermissionSearchResultDto = permissionRepository.findRoleAssignedByRoleIdAndAnyName(id,
                permissionName, pageable, account);
        return new PageImpl<>(assignedPermissionSearchResultDto.permissionList(), pageable,
                assignedPermissionSearchResultDto.total());
    }

    @Transactional
    public Page<Permission> searchUnassignPermission(final String id, final String name, final Pageable pageable,
        final Account account) {
        roleRepository.findAccessibleByPrimaryKey(id, account)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EWA4XX032));

        final var unassigendPermissionSearchResultDto = permissionRepository.findRoleUnassignedByRoleIdAndAnyName(id,
                name, pageable, account);
        return new PageImpl<>(unassigendPermissionSearchResultDto.permissionList(),
                pageable, unassigendPermissionSearchResultDto.total());

    }

    @Transactional
    public void assign(final String id, @NotEmpty final List<String> permissionIds, final Account account) {
        validateEditOperation(id, account);
        final var permissions = permissionRepository.findByPrimaryKeys(permissionIds);
        if (permissionIds.size() != permissions.size()) {
            throw new BadRequestException(ErrorCode.EWA4XX036);
        }
        final var rolePermissions = rolePermissionRepository.findByRoleIdAndPermissionIds(id, permissionIds);
        if (CollectionUtils.isNotEmpty(rolePermissions)) {
            throw new BadRequestException(ErrorCode.EWA4XX037);
        }

        permissionIds.forEach(permissionId -> {
            final var rolePermission = new RolePermission(null, id, permissionId, null, account.id());
            rolePermissionRepository.insert(rolePermission);
        });
    }

    @Transactional
    public void unassign(final String id, final List<String> permissionIds, final Account account) {

        validateEditOperation(id, account);
        final var permissions = permissionRepository.findByPrimaryKeys(permissionIds);
        if (permissionIds.size() != permissions.size()) {
            throw new BadRequestException(ErrorCode.EWA4XX038);
        }
        final var rolePermissions = rolePermissionRepository.findByRoleIdAndPermissionIds(id, permissionIds);
        if (rolePermissions.size() != permissionIds.size()) {
            throw new BadRequestException(ErrorCode.EWA4XX039);
        }
        rolePermissionRepository.deleteByRoleIdAndPermissionIds(id, permissionIds);
    }

    public void validateEntryOperation(final Account account) {
        if (!account.permissions().contains(PermissionEnum.ADD_ROLE)) {
            throw new ForbiddenException(ErrorCode.EWA4XX033);
        }
    }

    @Transactional
    public String create(final RoleEntryDto dto, final Account account) {
        validateEntryOperation(account);
        final var role = new Role(null, dto.name(), dto.description(), dto.grantable(), null, account.id(), null,
                account.id(), false);
        roleRepository.insert(role);
        return roleRepository.findByNameAndDescription(dto.name(), dto.description()).map(Role::getId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EWA4XX032));
    }
}
