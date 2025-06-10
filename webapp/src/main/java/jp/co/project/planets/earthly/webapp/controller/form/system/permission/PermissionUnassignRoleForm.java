package jp.co.project.planets.earthly.webapp.controller.form.system.permission;

import java.util.List;

import org.jilt.Builder;

import jakarta.validation.constraints.NotEmpty;

@Builder(factoryMethod = "builder")
public record PermissionUnassignRoleForm(@NotEmpty List<String> roleId) {
}
