package jp.co.project.planets.earthly.webapp.controller.form.role;

import java.io.Serializable;

import org.jilt.Builder;

/**
 * ロール編集FORM
 * 
 * @param name
 *            ロール名
 * @param description
 *            概要
 */
@Builder(factoryMethod = "builder")
public record RoleEditForm(String name, String description) implements Serializable {
}
