package jp.co.project.planets.earthly.webapp.controller.form.client;

import java.io.Serializable;

import org.jilt.Builder;

import jakarta.validation.constraints.NotBlank;
import jp.co.project.planets.earthly.webapp.annotation.validator.Url;

/**
 * OAuthクライアントログアウトリダイレクトURI登録FROM
 * 
 * @param redirectUri
 *            リダイレクトURI
 */
@Builder(factoryMethod = "builder")
public record OAuthClientLogoutRedirectUrlAddForm(@NotBlank @Url String redirectUri) implements Serializable {
}
