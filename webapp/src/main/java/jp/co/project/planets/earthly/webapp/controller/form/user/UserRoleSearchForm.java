package jp.co.project.planets.earthly.webapp.controller.form.user;

import org.jilt.Builder;

@Builder(factoryMethod = "builder")
public record UserRoleSearchForm(String roleName, Boolean isRemoveMode) {
}
