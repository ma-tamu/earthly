package jp.co.project.planets.earthly.webapp.controller.form.user;

import java.io.Serializable;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;

/**
 * ロール割り当てFORM
 * 
 * @param assign
 *            割り当てるロールID
 */
public record UserAssignRoleForm(@NotEmpty List<String> assign) implements Serializable {
}
