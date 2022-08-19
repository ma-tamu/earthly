package jp.co.project.planets.earthly.model.entity;

import java.io.Serializable;

/**
 * region entity
 *
 * @param id
 *         リージョンID
 * @param name
 *         リージョン名
 */
public record RegionEntity(String id, String name) implements Serializable {
}
