package jp.co.project.planets.earthly.webapp.model.dto;

import org.springframework.data.domain.Page;

import jp.co.project.planets.earthly.schema.db.entity.Company;
import jp.co.project.planets.earthly.schema.db.entity.Role;
import jp.co.project.planets.earthly.schema.model.entity.User;

public record UserDetailDto(User user, String qrcode, Page<Role> rolePage, Page<Role> unassignedRolePage,
        Page<Company> managementCompanyPage) {
}
