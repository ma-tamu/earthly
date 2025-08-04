package jp.co.project.planets.earthly.schema.model.dto;

import java.util.List;

import jp.co.project.planets.earthly.schema.db.entity.OauthClientRedirectUrl;

/**
 * OAuthクライアントリダイレクトURI検索結果DTO
 * 
 * @param oauthClientRedirectUriList
 *            OAuthクライアントリダイレクトURIリスト
 * @param offset
 *            オフセット
 * @param total
 *            総件数
 */
public record OAuthClientRedirectUriSearchResultDto(List<OauthClientRedirectUrl> oauthClientRedirectUriList,
        long offset, long total) {
}
