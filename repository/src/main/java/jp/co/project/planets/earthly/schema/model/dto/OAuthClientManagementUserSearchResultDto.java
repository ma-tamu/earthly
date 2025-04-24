package jp.co.project.planets.earthly.schema.model.dto;

import java.util.List;

import jp.co.project.planets.earthly.schema.model.entity.OAuthClientManagementUserEntity;

/**
 * OAuthクライアントリダイレクトURI検索結果DTO
 * 
 * @param oauthClientManagementUserList
 *            OAuthクライアント管理者リスト
 * @param offset
 *            オフセット
 * @param total
 *            総件数
 */
public record OAuthClientManagementUserSearchResultDto(
        List<OAuthClientManagementUserEntity> oauthClientManagementUserList, long offset, long total) {
}
