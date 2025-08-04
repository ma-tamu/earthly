package jp.co.project.planets.earthly.webapp.controller.form.company;

import java.io.Serializable;

import org.jilt.Builder;

/**
 * 会社検索FORM
 * 
 * @param name
 *            会社名
 */
@Builder(factoryMethod = "builder")
public record CompanySearchForm(String name) implements Serializable {
}
