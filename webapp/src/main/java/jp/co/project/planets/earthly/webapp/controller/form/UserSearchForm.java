package jp.co.project.planets.earthly.webapp.controller.form;

import java.io.Serializable;

/**
 * user search request
 *
 * @param loginId
 *         ログインID
 * @param name
 *         ユーザー名
 * @param company
 *         所属会社
 */
public record UserSearchForm(String loginId, String name, String company) implements Serializable {
}
