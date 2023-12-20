package jp.co.project.planets.earthly.webapp.controller.form.client;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jp.co.project.planets.earthly.webapp.annotation.validator.ValidScope;
import jp.co.project.planets.earthly.webapp.model.dto.OAuthClientEditDto;

/**
 * OAuthクライアント辺風FROM
 *
 * @param name
 *            名前
 * @param scope
 *            スコープ
 */
public record OAuthClientEditForm(@NotBlank String name, @NotEmpty List<@ValidScope String> scope) {

    /**
     * DTOへ変換
     *
     * @return OAuthClientEditDto
     */
    public OAuthClientEditDto toDto() {
        return new OAuthClientEditDto(name, scope);
    }
}
