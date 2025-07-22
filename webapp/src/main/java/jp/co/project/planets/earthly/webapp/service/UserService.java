package jp.co.project.planets.earthly.webapp.service;

import static jp.co.project.planets.earthly.webapp.emuns.ErrorCode.*;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.annotations.VisibleForTesting;

import jakarta.annotation.Nonnull;
import jp.co.project.planets.earthly.common.logic.CryptoLogic;
import jp.co.project.planets.earthly.common.logic.RoleLogic;
import jp.co.project.planets.earthly.common.logic.TotpLogic;
import jp.co.project.planets.earthly.common.logic.UserLogic;
import jp.co.project.planets.earthly.common.model.dto.UserDto;
import jp.co.project.planets.earthly.core.account.Account;
import jp.co.project.planets.earthly.schema.db.entity.Role;
import jp.co.project.planets.earthly.schema.db.entity.UserRole;
import jp.co.project.planets.earthly.schema.emuns.PermissionEnum;
import jp.co.project.planets.earthly.schema.model.entity.UserSimpleEntity;
import jp.co.project.planets.earthly.schema.repository.CompanyRepository;
import jp.co.project.planets.earthly.schema.repository.RoleRepository;
import jp.co.project.planets.earthly.schema.repository.UserRepository;
import jp.co.project.planets.earthly.schema.repository.UserRoleRepository;
import jp.co.project.planets.earthly.webapp.constant.MessageKey;
import jp.co.project.planets.earthly.webapp.constant.RegexConstant;
import jp.co.project.planets.earthly.webapp.exception.BadRequestException;
import jp.co.project.planets.earthly.webapp.exception.ForbiddenException;
import jp.co.project.planets.earthly.webapp.exception.NotFoundException;
import jp.co.project.planets.earthly.webapp.model.dto.UserDetailDto;
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
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    private final MessageSource messageSource;
    private final PasswordEncoder passwordEncoder;
    private final CryptoLogic cryptoLogic;

    private final TotpLogic totpLogic;

    private static final int MIN_PASSWORD_LENGTH = 8;
    private final RoleLogic roleLogic;

    /**
     * new instance user service
     *
     * @param userLogic
     *            user logic
     * @param userRepository
     *            user repository
     * @param companyRepository
     *            company repository
     * @param roleRepository
     *            role repository
     * @param userRoleRepository
     *            user role repository
     * @param messageSource
     *            message source
     * @param passwordEncoder
     *            password encoder
     * @param cryptoLogic
     *            crypto logic
     * @param totpLogic
     *            Time-based One-Time Password logic
     */
    public UserService(final UserLogic userLogic, final UserRepository userRepository,
        final CompanyRepository companyRepository, final RoleRepository roleRepository,
        final UserRoleRepository userRoleRepository, final MessageSource messageSource,
        final PasswordEncoder passwordEncoder, final CryptoLogic cryptoLogic, final TotpLogic totpLogic,
        final RoleLogic roleLogic) {
        this.userLogic = userLogic;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.messageSource = messageSource;
        this.passwordEncoder = passwordEncoder;
        this.cryptoLogic = cryptoLogic;
        this.totpLogic = totpLogic;
        this.roleLogic = roleLogic;
    }

    /**
     * 対象ユーザーを取得する。
     *
     * @param id
     *            ユーザーID
     * @param account
     *            ユーザー情報
     * @return UserEntity
     */
    @Transactional
    public UserDetailDto getDetail(final String id, final Account account) {
        validateAccessible(id, account);
        final var user = userLogic.getAccessibleEntity(id, account)
                .orElseThrow(() -> new NotFoundException(String.format("not found user user=%s.", id), EWA4XX002));

        final var pageRequest = PageRequest.of(0, 10);
        final var roleSearchResultDto = roleRepository.findAssignedRoleByUserIdAndLikeName(id, null, pageRequest,
                account);
        final var grantRolePage = new PageImpl<>(roleSearchResultDto.roleList(), pageRequest,
                roleSearchResultDto.total());

        final var unassignedRoleSearchResultDto = roleRepository.findUnassignedRoleByUserIdAndLikeName(id, null,
                pageRequest, account);
        final var unassignedRolePage = new PageImpl<>(unassignedRoleSearchResultDto.roleList(), pageRequest,
                unassignedRoleSearchResultDto.total());

        final var companyList = companyRepository.findByUserId(id, pageRequest, account);
        final var managementCompanyPage = new PageImpl<>(companyList.companyList(), pageRequest, companyList.total());

        if (BooleanUtils.isFalse(user.getTwoFactorAuthentication())) {
            return new UserDetailDto(user, null, grantRolePage, unassignedRolePage, managementCompanyPage);
        }
        final var image = totpLogic.generateQrImage(user.getLoginId(), user.getSecret());
        return new UserDetailDto(user, image, grantRolePage, unassignedRolePage, managementCompanyPage);
    }

    /**
     * 対象ユーザーが閲覧できるか検証する
     *
     * @param id
     *            ユーザーID
     * @param account
     *            ユーザー情報
     * @throws ForbiddenException
     *             対象ユーザー閲覧できない場合に発生
     */
    @VisibleForTesting
    void validateAccessible(final String id, final Account account) {

        // VIEW_ALL_USERを保持している場合は、すべてのユーザーを閲覧できるので検証処理を終了する
        if (account.permissions().contains(PermissionEnum.VIEW_ALL_USER)) {
            return;
        }

        // 自分自身の場合は閲覧可能のため検証処理を終了する
        if (Strings.CS.equals(id, account.id())) {
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
     * @param account
     *            ユーザー情報
     * @return 検索結果
     */
    @Transactional
    public PageImpl<UserSimpleEntity> search(final UserSearchDto userSearchDto, final Pageable pageable,
        final Account account) {

        final var userSearchResultDto = userRepository.findByLoginIdAndNameAndCompany(userSearchDto.loginId(),
                userSearchDto.name(), userSearchDto.company(), pageable, account);

        return new PageImpl<>(userSearchResultDto.userSimpleEntityList(), pageable, userSearchResultDto.total());
    }

    public void validateEntryOperation(final UserDto userDto, final Account account) {

        validateUserAddOperationPermission(account);
        validateUserAddingCompany(userDto.company(), account);

    }

    /**
     * ユーザー追加操作可能か検証
     *
     * @param account
     *            ユーザー情報
     */
    public void validateUserAddOperationPermission(final Account account) {
        if (account.permissions().contains(PermissionEnum.ADD_USER)) {
            return;
        }

        final var companyList = companyRepository.findAccessibleByUserId(account.id(), Optional.empty(),
                account);
        if (CollectionUtils.isEmpty(companyList)) {
            throw new ForbiddenException(EWA4XX004);
        }
    }

    /**
     * ユーザー追加可能な会社か検証
     *
     * @param companyId
     *            会社ID
     * @param account
     *            ユーザー情報
     * @throws ForbiddenException
     *             ユーザーを追加できない会社の場合に発生
     */
    void validateUserAddingCompany(final String companyId, final Account account) {
        final var companyOptional = companyRepository.findByAccessiblePrimaryKey(companyId, account);
        if (companyOptional.isEmpty()) {
            throw new ForbiddenException(EWA4XX004);
        }
    }

    /**
     * ユーザー作成
     *
     * @param userDto
     *            user dto
     * @param account
     *            ユーザー情報
     * @return ユーザーID
     */
    @Transactional
    public String create(final UserDto userDto, final Account account) {

        validateEntryOperation(userDto, account);

        final var user = userLogic.create(userDto, account.id()) //
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
     * @param account
     *            ユーザー情報
     * @throws ForbiddenException
     *             編集権限がないまたは、変更できない会社の場合に発生
     */
    public void validateUpdating(final String id, final UserDto userDto, final Account account) {
        final boolean canOperatingEdit = hasEditPermission(id, userDto, account);
        if (!canOperatingEdit) {
            throw new ForbiddenException(EWA4XX005);
        }

        final boolean canModifyBelongCompany = canModifyBelongCompany(userDto, account);
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
     * @param account
     *            user info dto
     * @return true: 編集可能 false: 編集不可
     */
    @VisibleForTesting
    boolean hasEditPermission(final String id, final UserDto userDto, final Account account) {

        // edit_userを持っている場合は、変更可能とする。
        if (account.permissions().contains(PermissionEnum.EDIT_USER)) {
            return true;
        }

        // 同じ所属会社の場合は、変更可能とする。
        if (account.permissions().contains(PermissionEnum.EDIT_MY_COMPANY_BRANCH)) {
            return Strings.CS.equals(userDto.company(), account.belongCompany().id());
        }

        // 自分自身の場合は、変更可能とする。
        return Strings.CS.equals(id, account.id());
    }

    /**
     * 所属会社の変更可能か
     *
     * @param userDto
     *            user dto
     * @param account
     *            user info dto
     * @return true: 変更可能 false: 変更不可能
     */
    @VisibleForTesting
    boolean canModifyBelongCompany(final UserDto userDto, final Account account) {

        // すべての会社が閲覧できる場合は、変更可能とする。
        if (account.permissions().contains(PermissionEnum.VIEW_ALL_COMPANY)) {
            return true;
        }

        // 操作ユーザーと同じ所属会社と同じ場合は、変更可能とする。
        final var afterCompanyId = userDto.company();
        if (Strings.CS.equals(afterCompanyId, account.belongCompany().id())) {
            return true;
        }

        // 管理している会社の場合は、変更可能とする。
        final var companyList = companyRepository.findManagementCompanyByUserId(account.id());
        return CollectionUtils.containsAny(companyList, afterCompanyId);
    }

    /**
     * パスワード編集
     * 
     * @param id
     *            ユーザーID
     * @param currentPassword
     *            操作ユーザーバスワード
     * @param newPassword
     *            新しいパスワード
     * @param confirmNewPassword
     *            新しいパスワードの確認
     * @param userInfoDto
     *            ユーザー情報
     * @throws BadRequestException
     *             操作ユーザーのパスワードが一致しない。または新しいパスワードと新しいパスワードの確認と一致しない場合に発生。
     */
    @Transactional
    public void editPassword(final String id, final String currentPassword, final String newPassword,
        final String confirmNewPassword, final EarthlyUserInfoDto userInfoDto) {

        if (!passwordEncoder.matches(currentPassword, userInfoDto.getPassword())) {
            throw new BadRequestException(EWA4XX011);
        }

        // パスワード強度の検証
        validateNewPasswordStrength(newPassword);
        if (!Strings.CS.equals(newPassword, confirmNewPassword)) {
            throw new BadRequestException(EWA4XX012);
        }

        final var user = userRepository.findByPrimaryKey(id).orElseThrow(() -> new NotFoundException(EWA4XX002));
        final var encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setUpdatedBy(userInfoDto.account().id());
        user.setUpdatedAt(LocalDateTime.now(Clock.systemUTC()));
        userRepository.update(user);
    }

    /**
     * 新しいパスワード強度の検証
     * 
     * @param newPassword
     *            新しいパスワード
     * @throws BadRequestException
     *             パスワードの最小桁数未満もしくは、大文字、小文字の英字、数字、記号が含まれていない場合に発生
     */
    @VisibleForTesting
    void validateNewPasswordStrength(final String newPassword) {

        if (newPassword.length() < MIN_PASSWORD_LENGTH) {
            throw new BadRequestException(EWA4XX013);
        }

        final var newPasswordMatcher = RegexConstant.PASSWORD_STRENGTH_PATTERN.matcher(newPassword);
        if (!newPasswordMatcher.matches()) {
            throw new BadRequestException(EWA4XX014);
        }
    }

    @Transactional
    public void updatePassword(@Nonnull final String id, @Nonnull final String newPassword,
        @Nonnull final String renewPassword) {

        if (!Strings.CS.equals(newPassword, renewPassword)) {
            throw new BadRequestException(EWA4XX007);
        }

        final var user = userRepository.findByPrimaryKey(id).orElseThrow(() -> new NotFoundException(EWA4XX002));
        final var password = passwordEncoder.encode(newPassword);
        user.setPassword(password);
        final var plant = String.join(":", user.getLoginId(), user.getMail());
        final var secret = cryptoLogic.encodeSHA256(plant);
        user.setSecret(secret);
        user.setUpdatedBy(id);
        userRepository.update(user);
    }

    /**
     * ユーザー更新
     *
     * @param id
     *            ユーザーID
     * @param userDto
     *            ユーザーDTO
     * @param account
     *            ユーザー情報
     * @return 更新メッセージ
     */
    @Transactional
    public String update(final String id, final UserDto userDto, final Account account) {

        validateUpdating(id, userDto, account);
        final var user = userRepository.findByPrimaryKey(id).orElseThrow(() -> new NotFoundException(EWA4XX002));
        userLogic.update(user, userDto, account.id());
        return messageSource.getMessage(MessageKey.UPDATE_SUCCESS, ArrayUtils.EMPTY_OBJECT_ARRAY, Locale.JAPAN);
    }

    /**
     * ユーザー削除
     *
     * @param id
     *            ユーザーID
     * @param account
     *            ユーザー情報
     * @return 削除メッセージ
     */
    public String delete(final String id, final Account account) {
        validateDeleteOperation(id, account);

        final var user = userRepository.findByPrimaryKey(id).orElseThrow(() -> new BadRequestException(EWA4XX002));
        user.setUpdatedBy(account.id());
        user.setIsDeleted(true);
        userRepository.update(user);
        return messageSource.getMessage(MessageKey.DELETE_SUCCESS, ArrayUtils.EMPTY_OBJECT_ARRAY, Locale.JAPAN);
    }

    /**
     * 削除操作の検証
     *
     * @param id
     *            ユーザーID
     * @param account
     *            ユーザー情報
     * @throws BadRequestException
     *             操作ユーザーと削除ユーザーが同じ場合に発生
     */
    @VisibleForTesting
    void validateDeleteOperation(@Nonnull final String id, final Account account) {
        if (Strings.CS.equals(id, account.id())) {
            throw new BadRequestException(EWA4XX002);
        }
        validateDeletePermission(id, account);
    }

    /**
     * 削除権限の検証
     *
     * @param id
     *            ユーザーID
     * @param account
     *            ユーザー情報
     * @throws ForbiddenException
     *             削除権限を保持していない場合に発生
     */
    void validateDeletePermission(final String id, final Account account) {

        if (account.permissions().contains(PermissionEnum.EDIT_USER)) {
            return;
        }

        if (!account.permissions().contains(PermissionEnum.EDIT_MY_COMPANY_BRANCH)) {
            return;
        }

        final var user = userRepository.findByPrimaryKey(id).orElseThrow(() -> new NotFoundException(EWA4XX002));
        if (Strings.CS.equals(user.getCompanyId(), account.belongCompany().id())) {
            return;
        }
        throw new ForbiddenException(EWA4XX008);
    }

    public Page<Role> findAssignedRole(final String id, final String roleName, final Pageable pageable,
        final Account account) {
        final var roleSearchResultDto = roleRepository.findAssignedRoleByUserIdAndLikeName(id, roleName,
                pageable, account);
        return new PageImpl<>(roleSearchResultDto.roleList(), pageable, roleSearchResultDto.total());
    }

    /**
     * 対象ユーザーが割り当てられてないロールを取得
     * 
     * @param id
     *            ユーザーID
     * @param roleName
     *            ロール名
     * @param pageable
     *            ページャー
     * @param account
     *            ユーザー情報
     * @return ロールページ
     */
    @Transactional
    public PageImpl<Role> findUnassignedRole(final String id, final String roleName, final Pageable pageable,
        final Account account) {
        final var roleSearchResultDto = roleRepository.findUnassignedRoleByUserIdAndLikeName(id, roleName, pageable,
                account);
        return new PageImpl<>(roleSearchResultDto.roleList(), pageable, roleSearchResultDto.total());
    }

    /**
     * ロール割り当て
     * 
     * @param id
     *            ユーザーID
     * @param assignRoleList
     *            割り当てるロールリスト
     * @param account
     *            ユーザー情報
     */
    @Transactional
    public void assignRole(final String id, final List<String> assignRoleList, final Account account) {

        validateAccessible(id, account);
        validateAssignableRole(id, assignRoleList, account);
        validateGrantingRoleDuplication(id, assignRoleList);

        for (final var roleId : assignRoleList) {
            final var userRole = new UserRole(null, id, roleId, LocalDateTime.now(ZoneOffset.UTC), account.id());
            userRoleRepository.insert(userRole);
        }
    }

    /**
     * 割り当てれるロールか検証
     * 
     * @param id
     *            ユーザーID
     * @param assignRoleList
     *            割り当てるロールリスト
     * @param account
     *            ユーザー情報
     * @throws ForbiddenException
     *             ロールを割り当てられない場合に発生
     */
    @VisibleForTesting
    void validateAssignableRole(final String id, final List<String> assignRoleList,
        final Account account) {
        final var roleSearchResultDto = roleRepository.findUnassignedRoleByUserIdAndLikeName(id, StringUtils.EMPTY,
                Pageable.ofSize(Integer.MAX_VALUE), account);
        final var unassignedRoleIdList = roleSearchResultDto.roleList().stream().map(Role::getId).toList();
        final var accessDeniedUnassignedRole = assignRoleList.stream().filter(s -> !unassignedRoleIdList.contains(s))
                .collect(Collectors.joining(", "));
        if (StringUtils.isNotBlank(accessDeniedUnassignedRole)) {
            throw new ForbiddenException(EWA4XX009);
        }
    }

    /**
     * 割り当てロールの重複検証
     * 
     * @param id
     *            ユーザーID
     * @param assignRoleList
     *            割り当てるロールリスト
     * @throws ForbiddenException
     *             割り当てロールが重複している場合に発生
     */
    @VisibleForTesting
    void validateGrantingRoleDuplication(final String id, final List<String> assignRoleList) {
        final var assignedRoleIdList = roleRepository.findByAssignedRoleByUserId(id).stream().map(Role::getId).toList();
        final boolean hasAssignedRole = assignRoleList.stream().anyMatch(assignedRoleIdList::contains);
        if (hasAssignedRole) {
            throw new ForbiddenException(EWA4XX009);
        }
    }

    @Transactional
    public void unassignedRole(final String id, final List<String> unassignedRoleList, final Account account) {
        validateAccessible(id, account);
        // validateAssignableRole(id, unassignedRoleList, userInfoDto);

        final var userRoleList = userRoleRepository.findByUserIdAndRoleId(id, unassignedRoleList);
        if (userRoleList.size() < unassignedRoleList.size()) {
            throw new BadRequestException(EWA4XX010);
        }
        for (final var userRole : userRoleList) {
            userRoleRepository.delete(userRole);
        }
    }

    void validate(final String id, final List<String> unassignedRoleList,
        final EarthlyUserInfoDto userInfoDto) {

    }
}
