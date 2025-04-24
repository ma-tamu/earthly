package jp.co.project.planets.earthly.webapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.project.planets.earthly.schema.emuns.PermissionEnum;
import jp.co.project.planets.earthly.schema.model.entity.CompanyEntity;
import jp.co.project.planets.earthly.schema.repository.CompanyRepository;
import jp.co.project.planets.earthly.schema.repository.CountryRepository;
import jp.co.project.planets.earthly.webapp.emuns.ErrorCode;
import jp.co.project.planets.earthly.webapp.exception.ForbiddenException;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CountryRepository countryRepository;

    public CompanyService(final CompanyRepository companyRepository, final CountryRepository countryRepository) {
        this.companyRepository = companyRepository;
        this.countryRepository = countryRepository;
    }

    @Transactional
    public Page<CompanyEntity> search(final String name, final Pageable pageable,
        final EarthlyUserInfoDto userInfoDto) {
        final var companySearchResultDto = companyRepository.findByLikeAnyName(name, userInfoDto.id(),
                userInfoDto.permissionEnumList(), pageable);
        return new PageImpl<>(companySearchResultDto.companyEntityList(), pageable, companySearchResultDto.total());
    }

    /**
     * Registers the entry action for a user. Verifies the required permissions
     * are available in
     * the provided user information. Throws an exception if the user does not
     * have the ADD_COMPANY
     * permission.
     *
     * @param userInfoDto
     *            the user information containing the user's permissions and
     *            other details
     * @throws ForbiddenException
     *             if the user lacks the ADD_COMPANY permission
     */
    @Transactional
    public void entry(final EarthlyUserInfoDto userInfoDto) {

        final var permissionEnumList = userInfoDto.permissionEnumList();
        if (!permissionEnumList.contains(PermissionEnum.ADD_COMPANY)) {
            throw new ForbiddenException(ErrorCode.EWA4XX020);
        }
    }
}
