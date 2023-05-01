package jp.co.project.planets.earthly.webapp.controller.form.user;

import java.io.Serializable;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;

/**
 * ロール解除FORM
 * 
 * @param unassigns
 *            ロールIDリスト
 */
public record UserUnassignedRoleForm(@NotEmpty List<String> unassigns) implements Serializable {
}
