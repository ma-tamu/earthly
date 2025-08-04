package jp.co.project.planets.earthly.webapp.controller.form.role;

import org.jilt.Builder;

@Builder(factoryMethod = "builder")
public record RoleAssignedPermissionSearchForm(String permissionName, Boolean isRemoveMode) {
}
