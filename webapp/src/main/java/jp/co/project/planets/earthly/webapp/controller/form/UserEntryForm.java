package jp.co.project.planets.earthly.webapp.controller.form;

import jp.co.project.planets.earthly.common.model.dto.UserEntryDto;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static jp.co.project.planets.earthly.webapp.emuns.ErrorMessageKey.*;

/**
 * user entry form
 *
 * @param loginId
 *         ログインID
 * @param name
 *         ユーザー名
 * @param mail
 *         メールアドレス
 * @param gender
 *         性別
 * @param company
 *         所属会社ID
 * @param companyName
 *         所属会社名
 */
public record UserEntryForm(
        @NotBlank @Length(min = 6, max = 32) @Pattern(regexp = "\\p{Alnum}+", message = "{" + VALIDATION_ALPHANUMERIC + "}") String loginId,
        @NotBlank @Length(min = 1, max = 128) String name, @NotBlank @Email String mail,
        @NotBlank @Pattern(regexp = "M|F|-") String gender, @NotBlank String company,
        String companyName) {

    public static final UserEntryForm EMPTY = new UserEntryForm(null, null, null, null, null, null);

    /**
     * convert to dto
     *
     * @return UserEntryDto
     */
    public UserEntryDto toDto() {
        return new UserEntryDto(loginId, name, mail, gender, company, companyName);
    }
}
