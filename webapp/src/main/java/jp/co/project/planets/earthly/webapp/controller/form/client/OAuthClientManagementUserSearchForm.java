package jp.co.project.planets.earthly.webapp.controller.form.client;

import java.io.Serializable;

/**
 * OAuthクライアント管理者検索FORM
 * 
 * @param userName
 *            ユーザー名
 * @param companyName
 *            所属会社名
 * @param isRemoveMode
 *            削除モード有無
 */
public record OAuthClientManagementUserSearchForm(String userName, String companyName, Boolean isRemoveMode)
        implements Serializable {

}
