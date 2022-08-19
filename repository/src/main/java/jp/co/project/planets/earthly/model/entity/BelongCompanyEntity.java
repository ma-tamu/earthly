package jp.co.project.planets.earthly.model.entity;

import java.io.Serializable;

/**
 * company entity
 *
 * @param id
 *         会社ID
 * @param name
 *         会社名
 * @param countryEntity
 *         所属国
 */
public record BelongCompanyEntity(String id, String name, CountryEntity countryEntity) implements Serializable {
}
