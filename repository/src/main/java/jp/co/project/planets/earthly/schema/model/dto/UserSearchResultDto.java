package jp.co.project.planets.earthly.schema.model.dto;

import java.util.List;

import jp.co.project.planets.earthly.schema.model.entity.UserSimpleEntity;

/**
 * ユーザー検索結果DTO
 *
 * @param userSimpleEntityList
 *            簡易ユーザーリスト
 * @param offset
 *            オフセット
 * @param total
 *            トータル件数
 */
public record UserSearchResultDto(List<UserSimpleEntity> userSimpleEntityList, long offset, long total) {
}
