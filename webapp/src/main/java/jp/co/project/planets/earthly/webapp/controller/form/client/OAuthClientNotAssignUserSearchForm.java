package jp.co.project.planets.earthly.webapp.controller.form.client;

import org.jilt.Builder;

/**
 * OAuthクライアント管理者検索FORM
 * 
 * @param loginId
 *            ログインID
 * @param userName
 *            ユーザー名
 * @param companyName
 *            所属会社名
 */
@Builder(factoryMethod = "builder")
public record OAuthClientNotAssignUserSearchForm(String loginId, String userName, String companyName) {

}
