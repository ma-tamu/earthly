package jp.co.project.planets.earthly.webapp.controller.form.system.permission;

import java.io.Serializable;

import org.jilt.Builder;

@Builder(factoryMethod = "builder")
public record PermissionUnassignedRoleSearchForm(String roleName) implements Serializable {
}
