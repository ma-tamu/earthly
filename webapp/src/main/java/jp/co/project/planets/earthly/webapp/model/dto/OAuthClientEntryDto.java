package jp.co.project.planets.earthly.webapp.model.dto;

import java.util.List;

/**
 * OAuthクライアント登録DTO
 *
 * @param name
 *            名前
 * @param scopes
 *            スコープ
 */
public record OAuthClientEntryDto(String name, List<String> scopes) {
}
