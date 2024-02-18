package jp.co.project.planets.earthly.webapp.controller.form.role;

import java.io.Serializable;

/**
 * ロール検索FORM
 * 
 * @param name
 *            ロール名
 */
public record RoleSearchForm(String name) implements Serializable {
}
