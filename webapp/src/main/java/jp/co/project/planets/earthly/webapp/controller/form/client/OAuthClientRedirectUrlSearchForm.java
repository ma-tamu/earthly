package jp.co.project.planets.earthly.webapp.controller.form.client;

import java.io.Serializable;

/**
 * OAuthクライアントリダイレクトURI検索FORM
 * 
 * @param redirectUri
 *            検索するリダイレクトURI
 * @param isRemoveMode
 *            削除モード有無
 */
public record OAuthClientRedirectUrlSearchForm(String redirectUri, Boolean isRemoveMode) implements Serializable {
}
