package jp.co.project.planets.earthly.schema.model.dto;

import java.util.List;

import jp.co.project.planets.earthly.schema.model.entity.CompanyEntity;

/**
 * 会社検索結果
 * 
 * @param companyEntityList
 *            会社リスト
 * @param total
 *            トータル件数
 */
public record CompanySearchResultDto(List<CompanyEntity> companyEntityList, long total) {
}
