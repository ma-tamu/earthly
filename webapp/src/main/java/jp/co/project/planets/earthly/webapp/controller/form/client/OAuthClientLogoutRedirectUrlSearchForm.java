package jp.co.project.planets.earthly.webapp.controller.form.client;

import java.io.Serializable;

/**
 * OAuthクライアントログアウトリダイレクトURI検索FORM
 * 
 * @param logoutRedirectUrl
 *            検索するログアウトリダイレクトURI
 * @param isRemoveMode
 *            削除モード有無
 */
public record OAuthClientLogoutRedirectUrlSearchForm(String logoutRedirectUrl, Boolean isRemoveMode)
        implements Serializable {
}
