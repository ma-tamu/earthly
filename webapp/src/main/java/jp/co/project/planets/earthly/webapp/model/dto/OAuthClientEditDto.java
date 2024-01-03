package jp.co.project.planets.earthly.webapp.model.dto;

import java.util.List;

/**
 * OAuthクライアント編集DTO
 *
 * @param name
 *            名前
 * @param scopes
 *            スコープ
 */
public record OAuthClientEditDto(String name, List<String> scopes) {
}
