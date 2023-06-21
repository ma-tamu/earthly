package jp.co.project.planets.earthly.webapp.controller.form.forgot;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * パスワードリセットFORM
 * 
 * @param mail
 *            パスワードをリセットするユーザーIDに紐づくメールアドレス
 */
public record ResetPasswordMailForm(@NotBlank String loginId, @NotBlank @Email String mail) {
}
