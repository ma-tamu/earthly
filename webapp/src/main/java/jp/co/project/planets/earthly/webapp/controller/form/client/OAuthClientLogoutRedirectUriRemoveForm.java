package jp.co.project.planets.earthly.webapp.controller.form.client;

import java.util.List;

import org.jilt.Builder;

import jakarta.validation.constraints.NotEmpty;

/**
 * OAuthクライアントログアウトリダイレクトURI削除FORM
 * 
 * @param logoutRedirectUriIds
 *            リダイレクトURI ID
 */
@Builder(factoryMethod = "builder")
public record OAuthClientLogoutRedirectUriRemoveForm(@NotEmpty List<String> logoutRedirectUriIds) {
}
