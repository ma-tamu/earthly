package jp.co.project.planets.earthly.webapp.controller.form.system.permission;

import java.io.Serializable;

import org.jilt.Builder;

@Builder(factoryMethod = "builder")
public record PermissionAssignedRoleSearchForm(String roleName, Boolean isRemoveMode) implements Serializable {
}
