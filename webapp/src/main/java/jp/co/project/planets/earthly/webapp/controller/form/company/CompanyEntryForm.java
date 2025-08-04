package jp.co.project.planets.earthly.webapp.controller.form.company;

import org.hibernate.validator.constraints.Length;
import org.jilt.Builder;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jp.co.project.planets.earthly.common.annotation.validate.Timezone;
import jp.co.project.planets.earthly.common.model.dto.CompanyEntryDto;
import jp.co.project.planets.earthly.webapp.annotation.validator.Alphanumeric;

/**
 * 会社登録FROM
 * 
 * @param name
 *            会社名
 * @param country
 *            所属国
 */
@Builder(factoryMethod = "builder")
public record CompanyEntryForm(@NotBlank @Size(min = 1, max = 64) String name, @NotBlank String country,
        @NotBlank @Length(min = 6, max = 32) @Alphanumeric String firstLoginId,
        @NotBlank @Length(min = 1, max = 128) String firstUserName, @NotBlank @Email String mail,
        @NotBlank @Pattern(regexp = "ja|en") String language, @NotBlank @Timezone String timezone,
        @NotBlank @Pattern(regexp = "[MF\\-]") String gender) {

    public CompanyEntryDto toDto() {
        return new CompanyEntryDto(name, country, firstLoginId, firstUserName, mail, language, timezone, gender);
    }
}
