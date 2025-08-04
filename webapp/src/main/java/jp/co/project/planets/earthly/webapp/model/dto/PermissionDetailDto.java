package jp.co.project.planets.earthly.webapp.model.dto;

import org.jilt.Builder;
import org.springframework.data.domain.Page;

import jp.co.project.planets.earthly.schema.db.entity.Permission;
import jp.co.project.planets.earthly.schema.db.entity.Role;

@Builder(factoryMethod = "builder")
public record PermissionDetailDto(Permission permission, Page<Role> assignedRolePage, Page<Role> unassignedRolePage) {
}
