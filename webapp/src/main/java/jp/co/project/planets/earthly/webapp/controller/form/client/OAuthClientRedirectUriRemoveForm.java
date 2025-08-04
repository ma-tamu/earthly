package jp.co.project.planets.earthly.webapp.controller.form.client;

import java.util.List;

import org.jilt.Builder;

import jakarta.validation.constraints.NotEmpty;

/**
 * OAuthクライアントリダイレクトURI削除FORM
 * 
 * @param redirectUriIds
 *            リダイレクトURI ID
 */
@Builder(factoryMethod = "builder")
public record OAuthClientRedirectUriRemoveForm(@NotEmpty List<String> redirectUriIds) {
}
