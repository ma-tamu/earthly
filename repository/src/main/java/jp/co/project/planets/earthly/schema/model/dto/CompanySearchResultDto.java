package jp.co.project.planets.earthly.schema.model.dto;

import java.util.List;

import jp.co.project.planets.earthly.schema.db.entity.Company;

/**
 * 会社検索結果
 * 
 * @param companyList
 *            会社リスト
 * @param offset
 *            オフセット
 * @param total
 *            トータル件数
 */
public record CompanySearchResultDto(List<Company> companyList, long offset, long total) {
}
