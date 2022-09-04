package jp.co.project.planets.earthly.webapp.model.dto;

/**
 * user entry dto
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
public record UserEntryDto(String loginId, String name, String mail, String gender, String company,
                           String companyName) {
}
