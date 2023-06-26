package jp.co.project.planets.earthly.schema.model.entity;

import java.io.Serializable;
import java.util.List;

import jp.co.project.planets.earthly.schema.db.entity.GrantType;
import jp.co.project.planets.earthly.schema.db.entity.Scope;

/**
 * oauth client entity
 *
 * @param id
 *            id
 * @param clientId
 *            クライアントID
 * @param secret
 *            クライアントシークレット
 * @param name
 *            クライアント名
 * @param scopes
 *            スコープ
 * @param grantTypes
 *            付与タイプ
 * @param redirectUrls
 *            リダイレクトURL
 * @param logoutRedirectUrls
 *            ログアウトリダイレクトURL
 */
public record OAuthClientEntity(String id, String clientId, String secret, String name, List<Scope> scopes,
        List<GrantType> grantTypes, List<String> redirectUrls, List<String> logoutRedirectUrls)
        implements Serializable {
}
