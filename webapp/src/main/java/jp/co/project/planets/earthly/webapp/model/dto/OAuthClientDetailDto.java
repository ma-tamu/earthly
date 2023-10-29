package jp.co.project.planets.earthly.webapp.model.dto;

import org.springframework.data.domain.PageImpl;

import jp.co.project.planets.earthly.schema.db.entity.LogoutRedirectUrl;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientRedirectUrl;
import jp.co.project.planets.earthly.schema.db.entity.User;
import jp.co.project.planets.earthly.schema.model.entity.OAuthClientDetailEntity;

/**
 * OAuthクライアント詳細DTO
 *
 * @param oauthClientDetailEntity
 *            OAuthクライアントエンティティ
 * @param redirectUrlPage
 *            リダイレクトURLページ
 * @param logoutRedirectUrlPage
 *            ログアウトリダイレクトURLページ
 * @param managementUserPage
 *            管理ユーザーページ
 * @param canEditableClient
 *            編集有無
 */
public record OAuthClientDetailDto(OAuthClientDetailEntity oauthClientDetailEntity,
        PageImpl<OauthClientRedirectUrl> redirectUrlPage, PageImpl<LogoutRedirectUrl> logoutRedirectUrlPage,
        PageImpl<User> managementUserPage, boolean canEditableClient) {
}
