package jp.co.project.planets.earthly.webapp.controller.form.user;

import static jp.co.project.planets.earthly.webapp.emuns.ErrorMessageKey.*;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jp.co.project.planets.earthly.common.annotation.validate.Timezone;
import jp.co.project.planets.earthly.common.model.dto.UserDto;

/**
 * user entry form
 *
 * @param loginId
 *            ログインID
 * @param name
 *            ユーザー名
 * @param mail
 *            メールアドレス
 * @param gender
 *            性別
 * @param company
 *            所属会社ID
 * @param companyName
 *            所属会社名
 */
public record UserEntryForm(
        @NotBlank @Length(min = 6, max = 32) @Pattern(regexp = "\\p{Alnum}+", message = "{" + VALIDATION_ALPHANUMERIC
                + "}") String loginId,
        @NotBlank @Length(min = 1, max = 128) String name, @NotBlank @Email String mail,
        @NotBlank @Pattern(regexp = "ja|en") String language, @NotBlank @Timezone String timezone,
        @NotBlank @Pattern(regexp = "[MF\\-]") String gender, @NotBlank String company,
        String companyName) implements Serializable {

    public static final UserEntryForm EMPTY = new UserEntryForm(null, null, null, null, null, null, null, null);

    /**
     * convert to dto
     *
     * @return UserEntryDto
     */
    public UserDto toDto() {
        return new UserDto(loginId, name, mail, gender, language, timezone, company, companyName, false, false);
    }
}
