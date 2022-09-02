package jp.co.project.planets.earthly.webapp.controller.form;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * user entry form
 */
public record UserEntryForm(@NotBlank @Length(min = 6, max = 32) @Pattern(regexp = "\\p{Alnum}") String loginId,
                            @NotBlank @Length(min = 1, max = 128) String name, @NotBlank @Email String mail,
                            @NotBlank @Pattern(regexp = "M|F") String gender, @NotBlank String company,
                            String companyName) {

    public static final UserEntryForm EMPTY = new UserEntryForm(null, null, null, null, null, null);

}
