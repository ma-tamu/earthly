package jp.co.project.planets.earthly.webapp.controller.form.user;

import java.io.Serializable;

/**
 * ユーザー未割当ロール検索FORM
 * 
 * @param roleName
 *            ロール名
 */
public record UserUnassignedRoleSearchForm(String roleName) implements Serializable {
}
