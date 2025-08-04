package jp.co.project.planets.earthly.webapp.controller.form.role;

import java.io.Serializable;

import org.jilt.Builder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jp.co.project.planets.earthly.webapp.model.dto.RoleEditDto;

/**
 * ロール編集FORM
 * 
 * @param name
 *            ロール名
 * @param description
 *            概要
 * @param grantable
 *            付与可能か
 */
@Builder(factoryMethod = "builder")
public record RoleEditForm(@NotBlank @Size(min = 1, max = 64) String name,
        @NotBlank @Size(min = 1, max = 255) String description, @NotNull Boolean grantable)
        implements Serializable {

    public RoleEditDto toDto() {
        return new RoleEditDto(name, description, grantable);
    }
}
