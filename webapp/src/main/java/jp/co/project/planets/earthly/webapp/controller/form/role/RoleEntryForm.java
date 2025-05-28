package jp.co.project.planets.earthly.webapp.controller.form.role;

import java.io.Serializable;

import org.jilt.Builder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jp.co.project.planets.earthly.webapp.annotation.validator.Alphanumeric;
import jp.co.project.planets.earthly.webapp.model.dto.RoleEntryDto;

/**
 * ロール登録FORM
 *
 * @param name
 *            ロール名
 * @param description
 *            概要
 * @param grantable
 *            不要可能
 */
@Builder(factoryMethod = "builder")
public record RoleEntryForm(@NotBlank @Size(min = 1, max = 64) @Alphanumeric String name,
        @NotBlank @Size(min = 1, max = 255) String description, @NotNull Boolean grantable) implements Serializable {

    public RoleEntryDto toDto() {
        return new RoleEntryDto(name, description, grantable);
    }
}
