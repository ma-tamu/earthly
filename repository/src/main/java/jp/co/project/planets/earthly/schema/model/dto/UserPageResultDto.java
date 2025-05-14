package jp.co.project.planets.earthly.schema.model.dto;

import java.util.List;

import org.jilt.Builder;

import jp.co.project.planets.earthly.schema.model.entity.User;

/**
 * ユーザーページDTO
 * 
 * @param userList
 *            ユーザーリスト
 * @param offset
 *            オフセット
 * @param total
 *            総件数
 */
@Builder(factoryMethod = "builder")
public record UserPageResultDto(List<User> userList, long offset, long total) {
}
