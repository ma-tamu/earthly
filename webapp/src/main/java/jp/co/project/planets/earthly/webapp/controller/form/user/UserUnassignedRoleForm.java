package jp.co.project.planets.earthly.webapp.controller.form.user;

import java.io.Serializable;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;

/**
 * ロール解除FORM
 * 
 * @param roleId
 *            ロールIDリスト
 */
public record UserUnassignedRoleForm(@NotEmpty List<String> roleId) implements Serializable {
}
