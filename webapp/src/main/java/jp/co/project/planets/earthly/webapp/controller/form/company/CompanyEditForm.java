package jp.co.project.planets.earthly.webapp.controller.form.company;

import org.jilt.Builder;

import jakarta.validation.constraints.NotBlank;
import jp.co.project.planets.earthly.webapp.model.dto.CompanyEditDto;

/**
 * 会社編集FORM
 * 
 * @param name
 *            会社名
 * @param country
 *            所属国
 */
@Builder(factoryMethod = "builder")
public record CompanyEditForm(@NotBlank String name, @NotBlank String country) {

    public CompanyEditDto toDto() {
        return new CompanyEditDto(name, country);
    }
}
