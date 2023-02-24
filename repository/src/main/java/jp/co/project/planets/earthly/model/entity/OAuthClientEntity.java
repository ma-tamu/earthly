package jp.co.project.planets.earthly.model.entity;

import jp.co.project.planets.earthly.db.entity.GrantType;
import jp.co.project.planets.earthly.db.entity.Scope;

import java.io.Serializable;
import java.util.List;

/**
 * oauth client entity
 *
 * @param id
 *         id
 * @param clientId
 *         クライアントID
 * @param secret
 *         クライアントシークレット
 * @param name
 *         クライアント名
 * @param scopes
 *         スコープ
 * @param grantTypes
 *         付与タイプ
 * @param redirectUrls
 *         リダイレクトURL
 */
public record OAuthClientEntity(
        /** id */
        String id,
        /** クライアントID */
        String clientId,
        /** クライアントシークレット */
        String secret,
        /** クライアント名 */
        String name,
        /** スコープ */
        List<Scope> scopes,
        /** 付与タイプ */
        List<GrantType> grantTypes,
        /** リダイレクトURL */
        List<String> redirectUrls
) implements Serializable {
}
