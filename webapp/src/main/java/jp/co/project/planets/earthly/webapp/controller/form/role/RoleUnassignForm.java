package jp.co.project.planets.earthly.webapp.controller.form.role;

import java.util.List;

import org.jilt.Builder;

import jakarta.validation.constraints.NotEmpty;

@Builder(factoryMethod = "builder")
public record RoleUnassignForm(@NotEmpty List<String> permissionId) {
}
