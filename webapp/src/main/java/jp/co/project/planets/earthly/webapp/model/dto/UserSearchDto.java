package jp.co.project.planets.earthly.webapp.model.dto;

import org.springframework.data.domain.Sort;

/**
 * ユーザー検索DTO
 *
 * @param loginId
 *         ログインID
 * @param name
 *         ユーザー名
 * @param company
 *         所属会社
 * @param offset
 *         オフセット
 * @param limit
 *         リミット
 * @param sort
 *         ソート
 */
public record UserSearchDto(String loginId, String name, String company, long offset, long limit, Sort sort) {
}
