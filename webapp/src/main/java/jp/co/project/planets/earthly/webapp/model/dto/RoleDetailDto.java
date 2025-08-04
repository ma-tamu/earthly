package jp.co.project.planets.earthly.webapp.model.dto;

import org.jilt.Builder;
import org.springframework.data.domain.Page;

import jp.co.project.planets.earthly.schema.db.entity.Permission;
import jp.co.project.planets.earthly.schema.db.entity.Role;
import jp.co.project.planets.earthly.schema.model.entity.User;

@Builder(factoryMethod = "builder")
public record RoleDetailDto(Role role, Page<User> grantedUserPage, Page<User> notGrantedUserPage,
        Page<Permission> assignedPermissionPage, Page<Permission> unassignedPermissionPage) {
}
