package jp.co.project.planets.earthly.webapp.controller.form.client;

import java.io.Serializable;

/**
 * OAuthクライアントログアウトリダイレクトURI検索FORM
 * 
 * @param logoutRedirectUri
 *            検索するログアウトリダイレクトURI
 * @param isRemoveMode
 *            削除モード有無
 */
public record OAuthClientLogoutRedirectUriSearchForm(String logoutRedirectUri, Boolean isRemoveMode)
        implements Serializable {
}
