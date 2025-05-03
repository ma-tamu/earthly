package jp.co.project.planets.earthly.schema.model.dto;

import java.util.List;

import org.jilt.Builder;

import jp.co.project.planets.earthly.schema.db.entity.User;

/**
 * 会社管理社結果DTO
 * 
 * @param userList
 *            ユーザーリスト
 * @param offset
 *            オフセット
 * @param total
 *            総件数
 */
@Builder(factoryMethod = "builder")
public record ManagementCompanyUserResultDto(List<User> userList, long offset, long total) {
}
