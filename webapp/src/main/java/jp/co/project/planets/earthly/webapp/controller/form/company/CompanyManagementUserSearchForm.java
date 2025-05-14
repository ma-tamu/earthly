package jp.co.project.planets.earthly.webapp.controller.form.company;

import org.jilt.Builder;

import jp.co.project.planets.earthly.webapp.model.dto.CompanyManagementUserSearchDto;

@Builder(factoryMethod = "builder")
public record CompanyManagementUserSearchForm(String loginId, String name, String companyName, Boolean isRemoveMode) {

    public CompanyManagementUserSearchDto toDto() {
        return new CompanyManagementUserSearchDto(loginId, name, companyName);
    }
}
