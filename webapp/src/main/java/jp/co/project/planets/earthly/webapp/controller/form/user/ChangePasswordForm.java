package jp.co.project.planets.earthly.webapp.controller.form.user;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jp.co.project.planets.earthly.webapp.annotation.validator.Password;

/**
 * change password from
 * 
 * @param newPassword
 *            新しいパスワード
 * @param renewPassword
 *            新しいパスワード再入力
 */
public record ChangePasswordForm(@NotBlank @Password String newPassword,
        @NotBlank @Password String renewPassword) implements Serializable {
}
