package jp.co.project.planets.earthly.webapp.controller.form.user;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jp.co.project.planets.earthly.common.model.dto.UserDto;
import jp.co.project.planets.earthly.webapp.controller.form.DtoConvertible;

public record UserUpdateForm(@NotBlank @Length(min = 1, max = 128) String name,
        @NotBlank @Email String mail, @NotBlank String company, @NotBlank String companyId, Boolean lockout)
        implements DtoConvertible<UserDto> {

    @Override
    public UserDto toDto() {
        return new UserDto(null, name, mail, null, companyId, company, lockout);
    }
}
