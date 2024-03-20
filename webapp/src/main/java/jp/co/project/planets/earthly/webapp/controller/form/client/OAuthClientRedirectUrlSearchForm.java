package jp.co.project.planets.earthly.webapp.controller.form.client;

import java.io.Serializable;

/**
 * OAuthクライアントリダイレクト検索FORM
 * 
 * @param redirectUrl
 *            検索するリダイレクトURL
 */
public record OAuthClientRedirectUrlSearchForm(String redirectUrl) implements Serializable {
}
