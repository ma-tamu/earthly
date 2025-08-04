package jp.co.project.planets.earthly.common.model.dto;

import org.jilt.Builder;

/**
 * 会社登録DTO
 * 
 * @param name
 *            会社名
 * @param country
 *            所属国
 * @param firstLoginId
 *            初期ユーザー ログインID
 * @param firstUserName
 *            初期ユーザー ユーザー名
 * @param mail
 *            初期ユーザー メールアドレス
 * @param language
 *            初期ユーザー 言語
 * @param timezone
 *            初期ユーザー タイムゾーン
 * @param gender
 *            初期ユーザー 性別
 */
@Builder(factoryMethod = "builder")
public record CompanyEntryDto(String name, String country, String firstLoginId, String firstUserName, String mail,
        String language, String timezone, String gender) {
}
