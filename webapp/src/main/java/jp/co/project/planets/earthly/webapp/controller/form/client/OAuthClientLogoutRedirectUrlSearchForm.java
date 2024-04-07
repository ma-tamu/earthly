package jp.co.project.planets.earthly.webapp.controller.form.client;

import java.io.Serializable;

/**
 * OAuthクライアントログアウトリダイレクト検索FORM
 * 
 * @param logoutRedirectUrl
 *            検索するログアウトリダイレクトURL
 */
public record OAuthClientLogoutRedirectUrlSearchForm(String logoutRedirectUrl) implements Serializable {
}
