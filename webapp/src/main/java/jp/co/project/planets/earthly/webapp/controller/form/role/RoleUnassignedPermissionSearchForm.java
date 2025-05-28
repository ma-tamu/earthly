package jp.co.project.planets.earthly.webapp.controller.form.role;

import org.jilt.Builder;

@Builder(factoryMethod = "builder")
public record RoleUnassignedPermissionSearchForm(String permissionName) {
}
