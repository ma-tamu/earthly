package jp.co.project.planets.earthly.schema.model.dto;

import java.util.List;

import org.jilt.Builder;

import jp.co.project.planets.earthly.schema.db.entity.Notice;

/**
 * お政らせ検索結果DTO
 * 
 * @param noticeList
 *            お知らせリスト
 * @param offset
 *            オフセット
 * @param total
 *            トータル件数
 */
@Builder(factoryMethod = "builder")
public record NoticeSearchResultDto(List<Notice> noticeList, long offset, long total) {
}
