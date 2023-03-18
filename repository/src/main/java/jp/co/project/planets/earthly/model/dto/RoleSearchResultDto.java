package jp.co.project.planets.earthly.model.dto;

import java.util.List;

import jp.co.project.planets.earthly.db.entity.Role;

/**
 * ロール検索結果DTO
 * 
 * @param roleList
 *            ロールリスト
 * @param offset
 *            オフセット
 * @param total
 *            トータル件数
 */
public record RoleSearchResultDto(List<Role> roleList, long offset, long total) {
}
