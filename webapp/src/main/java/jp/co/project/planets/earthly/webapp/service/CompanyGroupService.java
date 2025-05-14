package jp.co.project.planets.earthly.webapp.service;

import java.util.Locale;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.project.planets.earthly.schema.db.entity.Organization;
import jp.co.project.planets.earthly.schema.emuns.PermissionEnum;
import jp.co.project.planets.earthly.schema.repository.CompanyRepository;
import jp.co.project.planets.earthly.schema.repository.OrganizationRepository;
import jp.co.project.planets.earthly.schema.repository.OrganizationUserRepository;
import jp.co.project.planets.earthly.schema.repository.UserRepository;
import jp.co.project.planets.earthly.webapp.constant.MessageKey;
import jp.co.project.planets.earthly.webapp.emuns.ErrorCode;
import jp.co.project.planets.earthly.webapp.exception.ForbiddenException;
import jp.co.project.planets.earthly.webapp.exception.NotFoundException;
import jp.co.project.planets.earthly.webapp.logic.CompanyLogic;
import jp.co.project.planets.earthly.webapp.model.dto.CompanyGroupDetailDto;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;

@Service
public class CompanyGroupService {

    private final CompanyRepository companyRepository;
    private final OrganizationRepository organizationRepository;
    private final OrganizationUserRepository organizationUserRepository;
    private final UserRepository userRepository;

    private final CompanyLogic companyLogic;

    private final MessageSource messageSource;

    public CompanyGroupService(final CompanyRepository companyRepository,
        final OrganizationRepository organizationRepository,
        final OrganizationUserRepository organizationUserRepository, final UserRepository userRepository,
        final CompanyLogic companyLogic, final MessageSource messageSource) {
        this.companyRepository = companyRepository;
        this.organizationRepository = organizationRepository;
        this.organizationUserRepository = organizationUserRepository;
        this.userRepository = userRepository;
        this.companyLogic = companyLogic;
        this.messageSource = messageSource;
    }

    @Transactional
    public String entry(final String companyId, final String name, final EarthlyUserInfoDto userInfoDto) {

        if (!userInfoDto.permissionEnumList().contains(PermissionEnum.ADD_GROUP)) {
            throw new ForbiddenException(ErrorCode.EWA4XX030);
        }

        final var organization = new Organization(null, companyId, name, null, userInfoDto.id(), null, userInfoDto.id(),
                false);
        organizationRepository.insert(organization);
        return organizationRepository.findByCompanyIdAndName(companyId, name).map(Organization::getId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EWA4XX031));
    }

    @Transactional
    public CompanyGroupDetailDto detail(final String id, final String companyId, final EarthlyUserInfoDto userInfoDto) {

        final boolean hasViewAllCompany = userInfoDto.permissionEnumList().contains(PermissionEnum.VIEW_ALL_COMPANY);
        final var company = companyRepository.findAccessibleByPrimaryKey(companyId, hasViewAllCompany, userInfoDto.id())
                .orElseThrow(() -> new NotFoundException(ErrorCode.EWA4XX025));

        final var organization = organizationRepository
                .findAccessibleByPrimaryKey(id, hasViewAllCompany, userInfoDto.id())
                .orElseThrow(() -> new NotFoundException(ErrorCode.EWA4XX025));
        final var hasViewAllUser = userInfoDto.permissionEnumList().contains(PermissionEnum.VIEW_ALL_USER);
        final var pageable = PageRequest.of(0, 10);
        final var userPageResultDto = userRepository.findBelongOrganizationByLikeLoginIdAndName(null, null, companyId,
                id, hasViewAllUser, pageable, userInfoDto.id());
        final var belongUserPage = new PageImpl<>(userPageResultDto.userList(), pageable,
                userPageResultDto.total());
        final var notBelongUserPageResultDto = userRepository.findNotBelongOrganizationByLikeLoginIdAndName(null, null,
                companyId, id, hasViewAllUser, pageable, userInfoDto.id());
        final var notBelongUserPage = new PageImpl<>(notBelongUserPageResultDto.userList(), pageable,
                notBelongUserPageResultDto.total());
        return new CompanyGroupDetailDto(organization, belongUserPage, notBelongUserPage);
    }

    @Transactional
    public String update(final String id, final String companyId, final String name,
        final EarthlyUserInfoDto userInfoDto) {

        if (!companyLogic.canEditable(companyId, userInfoDto)) {
            throw new ForbiddenException(ErrorCode.EWA4XX026);
        }

        final var hasViewAllCompany = userInfoDto.permissionEnumList().contains(PermissionEnum.VIEW_ALL_COMPANY);
        organizationRepository.findAccessibleByPrimaryKey(id, hasViewAllCompany, userInfoDto.id())
                .orElseThrow(() -> new NotFoundException(ErrorCode.EWA4XX025));

        final var organization = new Organization(id, companyId, name, null, null, null, userInfoDto.id(), false);
        organizationRepository.update(organization);
        return messageSource.getMessage(MessageKey.UPDATE_SUCCESS, ArrayUtils.EMPTY_OBJECT_ARRAY, Locale.JAPAN);
    }

    @Transactional
    public void delete(final String id, final String companyId, final EarthlyUserInfoDto userInfoDto) {
        if (!companyLogic.canEditable(companyId, userInfoDto)) {
            throw new ForbiddenException(ErrorCode.EWA4XX026);
        }

        final var hasViewAllCompany = userInfoDto.permissionEnumList().contains(PermissionEnum.VIEW_ALL_COMPANY);
        organizationRepository.findAccessibleByPrimaryKey(id, hasViewAllCompany, userInfoDto.id())
                .orElseThrow(() -> new NotFoundException(ErrorCode.EWA4XX025));

        organizationUserRepository.deleteByGroupId(id);
        organizationRepository.delete(id);
    }
}
