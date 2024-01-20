package jp.co.project.planets.earthly.schema.model.entity;

import java.io.Serializable;
import java.util.List;

import jp.co.project.planets.earthly.schema.db.entity.GrantType;
import jp.co.project.planets.earthly.schema.db.entity.LogoutRedirectUrl;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientRedirectUrl;

/**
 * OAuthクライアント詳細
 * 
 * @param id
 *            OAuthクライアントID
 * @param clientId
 *            クライアントID
 * @param secret
 *            シークレット
 * @param name
 *            OAuthクライアント名
 * @param scopes
 *            スコープリスト
 * @param grantTypes
 *            付与タイプリスト
 * @param redirectUrls
 *            リダイレクトURLリスト
 * @param logoutRedirectUrls
 *            ログアウトリダイレクトURLリスト
 * @param managementUserList
 *            OAuthクライアント管理者リスト
 */
public record OAuthClientDetailEntity(String id, String clientId, String secret, String name, List<String> scopes,
        List<GrantType> grantTypes, List<OauthClientRedirectUrl> redirectUrls,
        List<LogoutRedirectUrl> logoutRedirectUrls, List<OAuthClientManagementUserEntity> managementUserList)
        implements Serializable {
}
