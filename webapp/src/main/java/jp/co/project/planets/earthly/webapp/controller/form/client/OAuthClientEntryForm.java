package jp.co.project.planets.earthly.webapp.controller.form.client;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jp.co.project.planets.earthly.webapp.annotation.validator.ValidScope;
import jp.co.project.planets.earthly.webapp.model.dto.OAuthClientEntryDto;

/**
 * OAuthクライアント登録FROM
 *
 * @param name
 *            名前
 * @param scope
 *            スコープ
 */
public record OAuthClientEntryForm(@NotBlank String name, @NotEmpty List<@ValidScope String> scope) {

    /**
     * DTOへ変換
     *
     * @return OAuthClientEntryDto
     */
    public OAuthClientEntryDto toDto() {
        return new OAuthClientEntryDto(name, scope);
    }
}
