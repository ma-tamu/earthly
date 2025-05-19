package jp.co.project.planets.earthly.webapp.service;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.project.planets.earthly.core.account.Account;
import jp.co.project.planets.earthly.schema.db.entity.Organization;
import jp.co.project.planets.earthly.schema.db.entity.OrganizationUser;
import jp.co.project.planets.earthly.schema.emuns.PermissionEnum;
import jp.co.project.planets.earthly.schema.model.entity.User;
import jp.co.project.planets.earthly.schema.repository.CompanyRepository;
import jp.co.project.planets.earthly.schema.repository.OrganizationRepository;
import jp.co.project.planets.earthly.schema.repository.OrganizationUserRepository;
import jp.co.project.planets.earthly.schema.repository.UserRepository;
import jp.co.project.planets.earthly.webapp.constant.MessageKey;
import jp.co.project.planets.earthly.webapp.emuns.ErrorCode;
import jp.co.project.planets.earthly.webapp.exception.BadRequestException;
import jp.co.project.planets.earthly.webapp.exception.ForbiddenException;
import jp.co.project.planets.earthly.webapp.exception.NotFoundException;
import jp.co.project.planets.earthly.webapp.logic.CompanyLogic;
import jp.co.project.planets.earthly.webapp.model.dto.CompanyGroupDetailDto;
import jp.co.project.planets.earthly.webapp.model.dto.GroupBelongUserSearchDto;

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
    public String entry(final String companyId, final String name, final Account account) {

        if (!account.permissions().contains(PermissionEnum.ADD_GROUP)) {
            throw new ForbiddenException(ErrorCode.EWA4XX030);
        }

        final var organization = new Organization(null, companyId, name, null, account.id(), null, account.id(),
                false);
        organizationRepository.insert(organization);
        return organizationRepository.findByCompanyIdAndName(companyId, name).map(Organization::getId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EWA4XX031));
    }

    @Transactional
    public CompanyGroupDetailDto detail(final String id, final String companyId, final Account account) {

        final var company = companyRepository.findAccessibleByPrimaryKey(companyId, account)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EWA4XX025));

        final var organization = organizationRepository.findAccessibleByPrimaryKey(id, account)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EWA4XX025));
        final var pageable = PageRequest.of(0, 10);
        final var userPageResultDto = userRepository.findBelongOrganizationByLikeLoginIdAndName(null, null, companyId,
                id, pageable, account);
        final var belongUserPage = new PageImpl<>(userPageResultDto.userList(), pageable,
                userPageResultDto.total());
        final var notBelongUserPageResultDto = userRepository.findNotBelongOrganizationByLikeLoginIdAndName(null, null,
                companyId, id, pageable, account);
        final var notBelongUserPage = new PageImpl<>(notBelongUserPageResultDto.userList(), pageable,
                notBelongUserPageResultDto.total());
        return new CompanyGroupDetailDto(organization, belongUserPage, notBelongUserPage);
    }

    @Transactional
    public String update(final String id, final String companyId, final String name, final Account account) {

        if (!companyLogic.canEditable(companyId, account)) {
            throw new ForbiddenException(ErrorCode.EWA4XX026);
        }

        organizationRepository.findAccessibleByPrimaryKey(id, account)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EWA4XX025));

        final var organization = new Organization(id, companyId, name, null, null, null, account.id(), false);
        organizationRepository.update(organization);
        return messageSource.getMessage(MessageKey.UPDATE_SUCCESS, ArrayUtils.EMPTY_OBJECT_ARRAY, Locale.JAPAN);
    }

    @Transactional
    public void delete(final String id, final String companyId, final Account account) {
        if (!companyLogic.canEditable(companyId, account)) {
            throw new ForbiddenException(ErrorCode.EWA4XX026);
        }

        organizationRepository.findAccessibleByPrimaryKey(id, account)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EWA4XX025));

        organizationUserRepository.deleteByGroupId(id);
        organizationRepository.delete(id);
    }

    @Transactional
    public Page<User> searchBelongUser(final String id, final String companyId,
        final GroupBelongUserSearchDto groupBelongUserSearchFormDto, final Pageable pageable,
        final Account account) {
        final var belongUserPageResultDto = userRepository.findBelongOrganizationByLikeLoginIdAndName(
                groupBelongUserSearchFormDto.loginId(), groupBelongUserSearchFormDto.name(), companyId, id,
                pageable, account);
        return new PageImpl<>(belongUserPageResultDto.userList(), pageable,
                belongUserPageResultDto.total());
    }

    @Transactional
    public Page<User> searchNotBelongUser(final String id, final String companyId,
        final GroupBelongUserSearchDto groupBelongUserSearchDto, final Pageable pageable,
        final Account account) {
        final var notBelongUserPageResultDto = userRepository.findNotBelongOrganizationByLikeLoginIdAndName(
                groupBelongUserSearchDto.loginId(), groupBelongUserSearchDto.name(), companyId, id, pageable, account);
        return new PageImpl<>(notBelongUserPageResultDto.userList(), pageable,
                notBelongUserPageResultDto.total());
    }

    @Transactional
    public void assign(final String id, final String companyId, final List<String> userIds, final Account account) {

        if (!companyLogic.canEditable(companyId, account)) {
            throw new ForbiddenException(ErrorCode.EWA4XX026);
        }

        final var userList = userRepository.findByPrimaryKeysAccessibly(userIds, account);
        if (userList.size() != userIds.size()) {
            throw new BadRequestException(ErrorCode.EWA4XX028);
        }

        for (final var userId : userIds) {
            final var organizationUser = new OrganizationUser(null, id, userId, null, account.id());
            organizationUserRepository.insert(organizationUser);
        }
    }

    @Transactional
    public void unassign(final String id, final String companyId, final List<String> userIds, final Account account) {

        if (!companyLogic.canEditable(companyId, account)) {
            throw new ForbiddenException(ErrorCode.EWA4XX026);
        }

        final var userList = userRepository.findByPrimaryKeysAccessibly(userIds, account);
        if (userList.size() != userIds.size()) {
            throw new BadRequestException(ErrorCode.EWA4XX028);
        }

        final var organizationUserList = organizationUserRepository.findByOrganizationIdAndInUserId(id, userIds);
        if (organizationUserList.size() != userIds.size()) {
            throw new BadRequestException(ErrorCode.EWA4XX029);
        }

        organizationUserRepository.deleteByOrganizationIdAndInUserId(id, userIds);
    }
}
