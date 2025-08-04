package jp.co.project.planets.earthly.schema.model.dto;

import java.util.List;

import jp.co.project.planets.earthly.schema.db.entity.LogoutRedirectUrl;

/**
 * OAuthクライアントログアウトリダイレクトURI検索結果DTO
 * 
 * @param oauthClientLogoutRedirectUriList
 *            OAuthクライアントログアウトリダイレクトURIリスト
 * @param offset
 *            オフセット
 * @param total
 *            総件数
 */
public record OAuthClientLogoutRedirectUriSearchResultDto(List<LogoutRedirectUrl> oauthClientLogoutRedirectUriList,
        long offset, long total) {
}
