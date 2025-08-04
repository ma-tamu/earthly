package jp.co.project.planets.earthly.webapp.model.dto;

import java.util.List;

import org.jilt.Builder;
import org.springframework.data.domain.Page;

import jp.co.project.planets.earthly.schema.db.entity.Company;
import jp.co.project.planets.earthly.schema.db.entity.Country;
import jp.co.project.planets.earthly.schema.model.entity.Organization;
import jp.co.project.planets.earthly.schema.model.entity.User;

@Builder(factoryMethod = "builder")
public record CompanyDetailDto(Company company, List<Country> countryList, Page<User> managementUserPage,
        Page<User> notManagementUserPage, Page<Organization> groupPage) {
}
