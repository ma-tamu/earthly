package jp.co.project.planets.earthly.webapp.model.dto;

import org.jilt.Builder;

import jp.co.project.planets.earthly.schema.db.entity.Company;

@Builder(factoryMethod = "builder")
public record CompanyDetailDto(Company company,
        java.util.List<jp.co.project.planets.earthly.schema.db.entity.Country> countryList,
        org.springframework.data.domain.PageImpl<jp.co.project.planets.earthly.schema.db.entity.User> managementUserPage) {
}
