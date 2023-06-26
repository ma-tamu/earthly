package jp.co.project.planets.earthly.webapp.controller.form.user;

/**
 * パスワード編集FROM
 * 
 * @param currentPassword
 *            操作ユーザーバスワード
 * @param newPassword
 *            新しいパスワード
 * @param confirmNewPassword
 *            新しいパスワードの確認
 */
public record PasswordEditForm(String currentPassword, String newPassword, String confirmNewPassword) {
}
