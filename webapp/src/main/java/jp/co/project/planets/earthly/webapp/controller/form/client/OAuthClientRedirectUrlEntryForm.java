package jp.co.project.planets.earthly.webapp.controller.form.client;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jp.co.project.planets.earthly.webapp.annotation.validator.Url;

/**
 * OAuthクライアントリダイレクトURL登録FROM
 * 
 * @param redirectUrl
 *            リダイレクトURL
 */
public record OAuthClientRedirectUrlEntryForm(@NotBlank @Url String redirectUrl) implements Serializable {
}
