package jp.co.project.planets.earthly.webapp.controller.form.company;

import java.io.Serializable;

/**
 * 会社検索FORM
 * 
 * @param name
 *            会社名
 */
public record CompanySearchForm(String name) implements Serializable {
}
