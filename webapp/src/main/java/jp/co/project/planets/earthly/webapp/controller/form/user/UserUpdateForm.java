package jp.co.project.planets.earthly.webapp.controller.form.user;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jp.co.project.planets.earthly.common.annotation.validate.Timezone;
import jp.co.project.planets.earthly.common.model.dto.UserDto;
import jp.co.project.planets.earthly.webapp.controller.form.DtoConvertible;

public record UserUpdateForm(@NotBlank @Length(min = 1, max = 128) String name,
        @NotBlank @Email String mail, @NotBlank @Pattern(regexp = "ja|en") String language,
        @NotBlank @Timezone String timezone, @NotBlank String company, @NotBlank String companyId, Boolean lockout,
        Boolean mfa)
        implements Serializable, DtoConvertible<UserDto> {

    @Override
    public UserDto toDto() {
        return new UserDto(null, name, mail, null, language, timezone, companyId, company, lockout, mfa);
    }
}
