package jp.co.project.planets.earthly.webapp.controller.form.role;

import java.io.Serializable;

import org.jilt.Builder;

/**
 * ロール登録FORM
 *
 * @param name
 *            ロール名
 * @param description
 *            概要
 */
@Builder(factoryMethod = "builder")
public record RoleEntryForm(String name, String description) implements Serializable {
}
