package jp.co.project.planets.earthly.schema.model.entity;

import java.io.Serializable;

/**
 * role simple entity
 *
 * @param id
 *         ロールID
 * @param name
 *         ロール名
 */
public record RoleSimpleEntity(String id, String name) implements Serializable {
}
