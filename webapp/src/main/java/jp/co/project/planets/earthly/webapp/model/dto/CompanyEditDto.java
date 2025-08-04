package jp.co.project.planets.earthly.webapp.model.dto;

import org.jilt.Builder;

/**
 * 会社編集DTO
 * 
 * @param name
 *            会社名
 * @param country
 *            所属国
 */
@Builder(factoryMethod = "builder")
public record CompanyEditDto(String name, String country) {
}
