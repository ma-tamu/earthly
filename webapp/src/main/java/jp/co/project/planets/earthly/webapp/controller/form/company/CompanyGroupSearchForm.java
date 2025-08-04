package jp.co.project.planets.earthly.webapp.controller.form.company;

import org.jilt.Builder;

@Builder(factoryMethod = "builder")
public record CompanyGroupSearchForm(String groupName) {
}
