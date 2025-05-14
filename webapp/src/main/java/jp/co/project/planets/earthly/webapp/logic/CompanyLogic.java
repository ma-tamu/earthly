package jp.co.project.planets.earthly.webapp.logic;

import org.springframework.stereotype.Component;

import jp.co.project.planets.earthly.schema.emuns.PermissionEnum;
import jp.co.project.planets.earthly.schema.repository.ManagementCompanyUserRepository;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;

@Component
public class CompanyLogic {

    private final ManagementCompanyUserRepository managementCompanyUserRepository;

    public CompanyLogic(final ManagementCompanyUserRepository managementCompanyUserRepository) {
        this.managementCompanyUserRepository = managementCompanyUserRepository;
    }

    public boolean canEditable(final String id, final EarthlyUserInfoDto userInfoDto) {

        if (userInfoDto.permissionEnumList().contains(PermissionEnum.EDIT_COMPANY)) {
            return true;
        }
        return managementCompanyUserRepository.findByUniqueKey(id, userInfoDto.id()).isPresent();
    }

}
