package jp.co.project.planets.earthly.model.entity;

import java.io.Serializable;

/**
 * company simple entity
 *
 * @param id
 *            会社ID
 * @param name
 *            会社名
 */
public record CompanySimpleEntity(String id, String name) implements Serializable {
}
