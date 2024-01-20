package jp.co.project.planets.earthly.webapp.controller.form.system.permission;

import java.io.Serializable;

/**
 * パーミッション検索FORM
 * 
 * @param name
 *            パーミッション名
 */
public record PermissionSearchForm(String name) implements Serializable {
}
