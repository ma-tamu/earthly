package jp.co.project.planets.earthly.schema.model.entity;

import java.io.Serializable;

/**
 * country entity
 */
public record CountryEntity(String id, String name, LanguageEntity languageEntity,
                            RegionEntity regionEntity) implements Serializable {
}
