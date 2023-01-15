package jp.co.project.planets.earthly.webapp.service;

import static jp.co.project.planets.earthly.webapp.emuns.ErrorCode.*;

import java.util.Locale;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.annotations.VisibleForTesting;

import jp.co.project.planets.earthly.common.logic.UserLogic;
import jp.co.project.planets.earthly.common.model.dto.UserDto;
import jp.co.project.planets.earthly.emuns.PermissionEnum;
import jp.co.project.planets.earthly.model.entity.UserEntity;
import jp.co.project.planets.earthly.model.entity.UserSimpleEntity;
import jp.co.project.planets.earthly.repository.CompanyRepository;
import jp.co.project.planets.earthly.repository.UserRepository;
import jp.co.project.planets.earthly.webapp.constant.MessageKey;
import jp.co.project.planets.earthly.webapp.exception.ForbiddenException;
import jp.co.project.planets.earthly.webapp.exception.NotFoundException;
import jp.co.project.planets.earthly.webapp.model.dto.UserSearchDto;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;

/**
 * user service
 */
@Service
public class UserService {

    private final UserLogic userLogic;

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    private final MessageSource messageSource;

    /**
     * new instance user service
     *
     * @param userLogic
     *            user logic
     * @param userRepository
     *            user repository
     * @param companyRepository
     *            company repository
     * @param messageSource
     *            message source
     */
    public UserService(final UserLogic userLogic, final UserRepository userRepository,
            final CompanyRepository companyRepository, final MessageSource messageSource) {
        this.userLogic = userLogic;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.messageSource = messageSource;
    }

    /**
     * 対象ユーザーを取得する。
     *
     * @param id
     *            ユーザーID
     * @param userInfoDto
     *            ユーザー情報
     * @return UserEntity
     */
    @Transactional
    public UserEntity getById(final String id, final EarthlyUserInfoDto userInfoDto) {

        validateAccessible(id, userInfoDto);

        return userLogic.getAccessibleEntity(id, userInfoDto.permissionEnumList(), userInfoDto.id()).orElseThrow(
                () -> new NotFoundException(String.format("not found user user=%s.", id), EWA4XX002));
    }

    /**
     * 対象ユーザーが閲覧できるか検証する
     *
     * @param id
     *            ユーザーID
     * @param userInfoDto
     *            ユーザー情報
     * @throws ForbiddenException
     *             対象ユーザー閲覧できない場合に発生
     */
    @VisibleForTesting
    void validateAccessible(final String id, final EarthlyUserInfoDto userInfoDto) {

        // VIEW_ALL_USERを保持している場合は、すべてのユーザーを閲覧できるので検証処理を終了する
        if (userInfoDto.permissionEnumList().contains(PermissionEnum.VIEW_ALL_USER)) {
            return;
        }

        // 自分自身の場合は閲覧可能のため検証処理を終了する
        if (StringUtils.equals(id, userInfoDto.id())) {
            return;
        }

        throw new ForbiddenException(String.format("not accessible user user=%s", id), EWA4XX003);
    }

    /**
     * ユーザー検索
     *
     * @param userSearchDto
     *            ユーザー検索DTO
     * @param pageable
     *            ページャー
     * @param userInfoDto
     *            ユーザー情報
     * @return 検索結果
     */
    @Transactional
    public PageImpl<UserSimpleEntity> search(final UserSearchDto userSearchDto, final Pageable pageable,
            final EarthlyUserInfoDto userInfoDto) {

        final var userSearchResultDto = userRepository.findByLoginIdAndNameAndCompany(userSearchDto.loginId(),
                userSearchDto.name(), userSearchDto.company(), pageable, userInfoDto.permissionEnumList(),
                userInfoDto.id());

        return new PageImpl<>(userSearchResultDto.userSimpleEntityList(), pageable, userSearchResultDto.total());
    }

    public void validateEntryOperation(final UserDto userDto, final EarthlyUserInfoDto userInfoDto) {

        validateUserAddOperationPermission(userInfoDto);
        validateUserAddingCompany(userDto.company(), userInfoDto);

    }

    /**
     * ユーザー追加操作可能か検証
     *
     * @param userInfoDto
     *            ユーザー情報
     */
    public void validateUserAddOperationPermission(final EarthlyUserInfoDto userInfoDto) {
        if (userInfoDto.permissionEnumList().contains(PermissionEnum.ADD_USER)) {
            return;
        }

        final var companyList = companyRepository.findAccessibleByUserId(userInfoDto.id(), Optional.empty(),
                userInfoDto.permissionEnumList());
        if (CollectionUtils.isEmpty(companyList)) {
            throw new ForbiddenException(EWA4XX004);
        }
    }

    /**
     * ユーザー追加可能な会社か検証
     *
     * @param companyId
     *            会社ID
     * @param userInfoDto
     *            ユーザー情報
     * @throws ForbiddenException
     *             ユーザーを追加できない会社の場合に発生
     */
    void validateUserAddingCompany(final String companyId, final EarthlyUserInfoDto userInfoDto) {
        final var companyOptional = companyRepository.findByAccessiblePrimaryKey(companyId,
                userInfoDto.permissionEnumList(), userInfoDto.id());
        if (companyOptional.isEmpty()) {
            throw new ForbiddenException(EWA4XX004);
        }
    }

    /**
     * ユーザー作成
     * 
     * @param userDto
     * @param userInfoDto
     * @return
     */
    @Transactional
    public String create(final UserDto userDto, final EarthlyUserInfoDto userInfoDto) {

        validateEntryOperation(userDto, userInfoDto);

        final var user = userLogic.create(userDto, userInfoDto.id()) //
                .orElseThrow(() -> new NotFoundException(EWA4XX002));
        return user.getId();
    }

    /**
     * 編集内容の検証
     * 
     * @param id
     *            ユーザーID
     * @param userDto
     *            ユーザーDTO
     * @param userInfoDto
     *            ユーザー情報
     * @throws ForbiddenException
     *             編集権限がないまたは、変更できない会社の場合に発生
     */
    public void validateUpdating(final String id, final UserDto userDto, final EarthlyUserInfoDto userInfoDto) {
        final boolean canOperatingEdit = hasEditPermission(id, userDto, userInfoDto);
        if (!canOperatingEdit) {
            throw new ForbiddenException(EWA4XX005);
        }

        final boolean canModifyBelongCompany = canModifyBelongCompany(userDto, userInfoDto);
        if (!canModifyBelongCompany) {
            throw new ForbiddenException(EWA4XX006);
        }
    }

    /**
     * 編集権限を持っているか
     * 
     * @param id
     *            id
     * @param userDto
     *            user dto
     * @param userInfoDto
     *            user info dto
     * @return true: 編集可能 false: 編集不可
     */
    @VisibleForTesting
    boolean hasEditPermission(final String id, final UserDto userDto, final EarthlyUserInfoDto userInfoDto) {

        // edit_userを持っている場合は、変更可能とする。
        if (userInfoDto.permissionEnumList().contains(PermissionEnum.EDIT_USER)) {
            return true;
        }

        // 同じ所属会社の場合は、変更可能とする。
        if (userInfoDto.permissionEnumList().contains(PermissionEnum.EDIT_MY_COMPANY_BRANCH)) {
            return StringUtils.equals(userDto.company(), userInfoDto.company().id());
        }

        // 自分自身の場合は、変更可能とする。
        return StringUtils.equals(id, userInfoDto.id());
    }

    /**
     * 所属会社の変更可能か
     * 
     * @param userDto
     *            user dto
     * @param userInfoDto
     *            user info dto
     * @return true: 変更可能 false: 変更不可能
     */
    @VisibleForTesting
    boolean canModifyBelongCompany(final UserDto userDto, final EarthlyUserInfoDto userInfoDto) {

        // すべての会社が閲覧できる場合は、変更可能とする。
        if (userInfoDto.permissionEnumList().contains(PermissionEnum.VIEW_ALL_COMPANY)) {
            return true;
        }

        // 操作ユーザーと同じ所属会社と同じ場合は、変更可能とする。
        final var afterCompanyId = userDto.company();
        if (StringUtils.equals(afterCompanyId, userInfoDto.company().id())) {
            return true;
        }

        // 管理している会社の場合は、変更可能とする。
        final var companyList = companyRepository.findManagementCompanyByUserId(userInfoDto.id());
        return companyList.contains(afterCompanyId);
    }

    /**
     * ユーザー更新
     * 
     * @param id
     *            ユーザーID
     * @param userDto
     *            ユーザーDTO
     * @param userInfoDto
     *            ユーザー情報
     * @return 更新メッセージ
     */
    @Transactional
    public String update(final String id, final UserDto userDto, final EarthlyUserInfoDto userInfoDto) {

        validateUpdating(id, userDto, userInfoDto);
        final var user = userRepository.findByPrimaryKey(id).orElseThrow(() -> new NotFoundException(EWA4XX002));
        userLogic.update(user, userDto, userInfoDto.id());
        return messageSource.getMessage(MessageKey.UPDATE_SUCCESS, ArrayUtils.EMPTY_OBJECT_ARRAY, Locale.JAPAN);
    }

    public String delete(final String id, final EarthlyUserInfoDto userInfoDto) {
        return null;
    }
}
