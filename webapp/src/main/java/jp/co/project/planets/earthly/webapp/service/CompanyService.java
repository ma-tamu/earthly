package jp.co.project.planets.earthly.webapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.project.planets.earthly.common.logic.UserLogic;
import jp.co.project.planets.earthly.common.model.dto.CompanyEntryDto;
import jp.co.project.planets.earthly.common.model.dto.UserDto;
import jp.co.project.planets.earthly.core.account.Account;
import jp.co.project.planets.earthly.schema.db.entity.Company;
import jp.co.project.planets.earthly.schema.db.entity.ManagementCompanyUser;
import jp.co.project.planets.earthly.schema.emuns.PermissionEnum;
import jp.co.project.planets.earthly.schema.model.entity.Organization;
import jp.co.project.planets.earthly.schema.model.entity.User;
import jp.co.project.planets.earthly.schema.repository.CompanyRepository;
import jp.co.project.planets.earthly.schema.repository.CountryRepository;
import jp.co.project.planets.earthly.schema.repository.ManagementCompanyUserRepository;
import jp.co.project.planets.earthly.schema.repository.OrganizationRepository;
import jp.co.project.planets.earthly.schema.repository.UserRepository;
import jp.co.project.planets.earthly.webapp.constant.MessageKey;
import jp.co.project.planets.earthly.webapp.emuns.ErrorCode;
import jp.co.project.planets.earthly.webapp.exception.BadRequestException;
import jp.co.project.planets.earthly.webapp.exception.ForbiddenException;
import jp.co.project.planets.earthly.webapp.exception.NotFoundException;
import jp.co.project.planets.earthly.webapp.model.dto.CompanyDetailDto;
import jp.co.project.planets.earthly.webapp.model.dto.CompanyEditDto;
import jp.co.project.planets.earthly.webapp.model.dto.CompanyManagementUserSearchDto;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;

/**
 * 会社サービス
 */
@Service
public class CompanyService {

    private final UserLogic userLogic;

    private final CompanyRepository companyRepository;
    private final CountryRepository countryRepository;
    private final ManagementCompanyUserRepository managementCompanyUserRepository;
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;

    private final MessageSource messageSource;

    public CompanyService(final UserLogic userLogic, final CompanyRepository companyRepository,
        final CountryRepository countryRepository,
        final ManagementCompanyUserRepository managementCompanyUserRepository,
        final OrganizationRepository organizationRepository, final UserRepository userRepository,
        final MessageSource messageSource) {
        this.userLogic = userLogic;
        this.companyRepository = companyRepository;
        this.countryRepository = countryRepository;
        this.managementCompanyUserRepository = managementCompanyUserRepository;
        this.organizationRepository = organizationRepository;
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    /**
     * 会社検索
     * 
     * @param name
     *            会社名
     * @param pageable
     *            ページャー
     * @param account
     *            ユーザー情報
     * @return 検索結果
     */
    @Transactional
    public Page<Company> search(final String name, final Pageable pageable, final Account account) {
        final var companySearchResultDto = companyRepository.findByLikeAnyName(name, pageable, account);
        return new PageImpl<>(companySearchResultDto.companyList(), pageable, companySearchResultDto.total());
    }

    /**
     * Registers the entry action for a user. Verifies the required permissions
     * are available in
     * the provided user information. Throws an exception if the user does not
     * have the ADD_COMPANY
     * permission.
     *
     * @param account
     *            the user information containing the user's permissions and
     *            other details
     * @throws ForbiddenException
     *             if the user lacks the ADD_COMPANY permission
     */
    @Transactional
    public void validateEntryPermission(final Account account) {
        if (!account.permissions().contains(PermissionEnum.ADD_COMPANY)) {
            throw new ForbiddenException(ErrorCode.EWA4XX020);
        }
    }

    /**
     * 会社登録
     * 
     * @param companyEntryDto
     *            会社登録DTO
     * @param account
     *            ユーザー情報
     * @return 登録した会社のID
     * @throws NotFoundException
     *             所属国が存在しない場合に発生
     */
    @Transactional
    public String create(final CompanyEntryDto companyEntryDto, final Account account) {

        validateEntryPermission(account);

        final var country = countryRepository.findByPrimaryKey(companyEntryDto.country())
                .orElseThrow(() -> new NotFoundException(ErrorCode.EWA4XX024));

        final var currentDateTime = LocalDateTime.now();
        final var company = new Company(null, companyEntryDto.name(), country.getId(), currentDateTime,
                account.id(), currentDateTime, account.id(), false);
        companyRepository.insert(company);
        final var createdCompany = companyRepository.findByName(company.getName())
                .orElseThrow(() -> new NotFoundException(ErrorCode.EWA4XX002));

        final var userDto = new UserDto(companyEntryDto.firstLoginId(), companyEntryDto.firstUserName(),
                companyEntryDto.mail(), companyEntryDto.gender(), companyEntryDto.language(),
                companyEntryDto.timezone(), createdCompany.getId(), createdCompany.getName(), false, false);
        final var user = userLogic.create(userDto, account.id()).orElseThrow();

        final var managementCompanyUser = new ManagementCompanyUser(null, createdCompany.getId(), user.getId(),
                currentDateTime, account.id(), currentDateTime, account.id(), false);
        managementCompanyUserRepository.insert(managementCompanyUser);
        return company.getId();
    }

    /**
     * 会社詳細の取得
     * 
     * @param id
     *            会社ID
     * @param account
     *            ユーザー情報
     * @return 会社詳細
     */
    @Transactional
    public CompanyDetailDto detail(final String id, final Account account) {

        final var company = companyRepository.findAccessibleByPrimaryKey(id, account)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EWA4XX025));
        final var countryList = countryRepository.findAll();

        final var pageable = PageRequest.of(0, 10);
        final var managementCompanyUserResultDto = userRepository.findCompanyManagerByLikeLoginIdAndNameAndCompanyName(
                null, null, id, null, pageable, account);
        final var managementUserPage = new PageImpl<>(managementCompanyUserResultDto.userList(), pageable,
                managementCompanyUserResultDto.total());

        final var notManagementCompanyUserResultDto = userRepository.findNotCompanyManagerByNameAndCompanyName(null,
                null, null, id, pageable, account);
        final var notManagementUserPage = new PageImpl<>(notManagementCompanyUserResultDto.userList(), pageable,
                notManagementCompanyUserResultDto.total());

        final var groupPageResultDto = organizationRepository.findByCompanyIdAndLikeName(id, null, pageable);
        final var groupPage = new PageImpl<>(groupPageResultDto.organizationList(), pageable,
                groupPageResultDto.total());

        return new CompanyDetailDto(company, countryList, managementUserPage, notManagementUserPage, groupPage);
    }

    @Transactional
    public void validateEdit(final String id, final CompanyEditDto companyEditDto, final Account account) {

        validateEditPermission(id, account);
        companyRepository.findByPrimaryKey(id).orElseThrow(() -> new NotFoundException(ErrorCode.EWA4XX025));

        countryRepository.findByPrimaryKey(companyEditDto.country())
                .orElseThrow(() -> new NotFoundException(ErrorCode.EWA4XX024));

    }

    private void validateEditPermission(final String id, final Account account) {
        if (!account.permissions().contains(PermissionEnum.EDIT_COMPANY)) {
            managementCompanyUserRepository.findByUniqueKey(id, account)
                    .orElseThrow(() -> new BadRequestException(ErrorCode.EWA4XX026));
        }
    }

    /**
     * 会社更新
     *
     * @param id
     *            会社ID
     * @param companyEditDto
     *            会社編集DTO
     * @param account
     *            ユーザー情報
     * @return メッセージ
     */
    @Transactional
    public String update(final String id, final CompanyEditDto companyEditDto, final Account account) {
        validateEdit(id, companyEditDto, account);

        final var company = new Company(id, companyEditDto.name(), companyEditDto.country(), null, null,
                LocalDateTime.now(), account.id(), null);
        companyRepository.update(company);
        return messageSource.getMessage(MessageKey.UPDATE_SUCCESS, ArrayUtils.EMPTY_OBJECT_ARRAY, Locale.JAPAN);
    }

    /**
     * 会社削除
     * 
     * @param id
     *            会社ID
     * @param account
     *            ユーザー情報
     * @throws BadRequestException
     *             削除操作ができない場合に発生
     */
    @Transactional
    public void delete(final String id, final Account account) {

        if (!account.permissions().contains(PermissionEnum.EDIT_COMPANY)) {
            managementCompanyUserRepository.findByUniqueKey(id, account)
                    .orElseThrow(() -> new BadRequestException(ErrorCode.EWA4XX026));
        }

        managementCompanyUserRepository.deleteByCompanyId(id);
        organizationRepository.deleteByCompanyId(id);

        final var company = companyRepository.findByPrimaryKey(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EWA4XX025));
        company.setIsDeleted(Boolean.TRUE);
        companyRepository.update(company);
    }

    @Transactional
    public Page<User> searchManagementUser(final String id,
        final CompanyManagementUserSearchDto companyManagementUserSearchDto, final Pageable pageable,
        final Account account) {
        final var managementCompanyUserResultDto = userRepository.findCompanyManagerByLikeLoginIdAndNameAndCompanyName(
                companyManagementUserSearchDto.loginId(), companyManagementUserSearchDto.name(), id,
                companyManagementUserSearchDto.companyName(), pageable, account);
        return new PageImpl<>(managementCompanyUserResultDto.userList(), pageable,
                managementCompanyUserResultDto.total());
    }

    @Transactional
    public Page<User> searchNotAssignUserUser(final String id,
        final CompanyManagementUserSearchDto companyManagementUserSearchDto, final Pageable pageable,
        final Account account) {
        final var managementCompanyUserResultDto = userRepository.findNotCompanyManagerByNameAndCompanyName(
                companyManagementUserSearchDto.loginId(), companyManagementUserSearchDto.name(),
                companyManagementUserSearchDto.companyName(), id, pageable, account);
        return new PageImpl<>(managementCompanyUserResultDto.userList(), pageable,
                managementCompanyUserResultDto.total());
    }

    @Transactional
    public void assignManagementUser(final String id, final List<String> userIdList,
        final Account account) {

        validateEditPermission(id, account);

        final var company = companyRepository.findAccessibleByPrimaryKey(id, account)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EWA4XX025));

        final var userList = userRepository.findByPrimaryKeysAccessibly(userIdList, account);
        if (userIdList.size() != userList.size()) {
            throw new BadRequestException(ErrorCode.EWA4XX028);
        }

        final var managementCompanyUserList = managementCompanyUserRepository.findByCompanyIdAndInUserId(id,
                userIdList);
        if (CollectionUtils.isNotEmpty(managementCompanyUserList)) {
            throw new BadRequestException(ErrorCode.EWA4XX027);
        }

        userList.forEach(user -> {
            final var managementCompanyUser = new ManagementCompanyUser(null, company.getId(), user.getId(),
                    LocalDateTime.now(), account.id(), LocalDateTime.now(), account.id(), false);
            managementCompanyUserRepository.insert(managementCompanyUser);
        });
    }

    @Transactional
    public void unassignManagementUser(final String id, final List<String> userIdList, final Account account) {

        validateEditPermission(id, account);

        companyRepository.findAccessibleByPrimaryKey(id, account)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EWA4XX025));

        final var userList = userRepository.findByPrimaryKeysAccessibly(userIdList, account);
        if (userIdList.size() != userList.size()) {
            throw new BadRequestException(ErrorCode.EWA4XX028);
        }

        final var managementCompanyUserList = managementCompanyUserRepository.findByCompanyIdAndInUserId(id,
                userIdList);
        if (userIdList.size() != managementCompanyUserList.size()) {
            throw new BadRequestException(ErrorCode.EWA4XX029);
        }
        managementCompanyUserRepository.deleteByCompanyIdAndInUserId(id, userIdList);
    }

    @Transactional
    public Page<Organization> searchGroup(final String id, final String groupName, final Pageable pageable,
        final EarthlyUserInfoDto userInfoDto) {
        final var groupPageResultDto = organizationRepository.findByCompanyIdAndLikeName(id, groupName, pageable);
        return new PageImpl<>(groupPageResultDto.organizationList(), pageable, groupPageResultDto.total());
    }
}
