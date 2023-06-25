package jp.co.project.planets.earthly.schema.model.entity;

import java.io.Serializable;

/**
 * language entity
 *
 * @param id
 *         言語ID
 * @param name
 *         言語名
 */
public record LanguageEntity(String id, String name) implements Serializable {
}
