package jp.co.project.planets.earthly.model.entity;

import java.io.Serializable;

/**
 * country entity
 */
public record CountryEntity(String id, String name, LanguageEntity languageEntity,
                            RegionEntity regionEntity) implements Serializable {
}
