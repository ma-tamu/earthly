package jp.co.project.planets.earthly.webapp.model.dto;

import org.jilt.Builder;
import org.springframework.data.domain.Page;

import jp.co.project.planets.earthly.schema.model.entity.Organization;
import jp.co.project.planets.earthly.schema.model.entity.User;

@Builder(factoryMethod = "builder")
public record CompanyGroupDetailDto(Organization organization, Page<User> belongUserPage,
        Page<User> notBelongUserPage) {
}
