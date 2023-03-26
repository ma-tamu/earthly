package jp.co.project.planets.earthly.webapp.service;

import static jp.co.project.planets.earthly.webapp.emuns.ErrorCode.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.google.common.annotations.VisibleForTesting;

import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.util.Utils;
import jp.co.project.planets.earthly.common.logic.CryptoLogic;
import jp.co.project.planets.earthly.common.logic.UserLogic;
import jp.co.project.planets.earthly.common.model.dto.UserDto;
import jp.co.project.planets.earthly.db.entity.Role;
import jp.co.project.planets.earthly.db.entity.UserRole;
import jp.co.project.planets.earthly.emuns.PermissionEnum;
import jp.co.project.planets.earthly.model.entity.UserSimpleEntity;
import jp.co.project.planets.earthly.repository.CompanyRepository;
import jp.co.project.planets.earthly.repository.RoleRepository;
import jp.co.project.planets.earthly.repository.UserRepository;
import jp.co.project.planets.earthly.repository.UserRoleRepository;
import jp.co.project.planets.earthly.webapp.constant.MessageKey;
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
     */
    public UserService(final UserLogic userLogic, final UserRepository userRepository,
            final CompanyRepository companyRepository, final RoleRepository roleRepository,
            final UserRoleRepository userRoleRepository, final MessageSource messageSource,
            final PasswordEncoder passwordEncoder, final CryptoLogic cryptoLogic) {
        this.userLogic = userLogic;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.messageSource = messageSource;
        this.passwordEncoder = passwordEncoder;
        this.cryptoLogic = cryptoLogic;
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
    public UserDetailDto getById(final String id, final EarthlyUserInfoDto userInfoDto) {
        validateAccessible(id, userInfoDto);
        final var userEntity = userLogic.getAccessibleEntity(id, userInfoDto.permissionEnumList(), userInfoDto.id())
                .orElseThrow(() -> new NotFoundException(String.format("not found user user=%s.", id), EWA4XX002));
        if (!userEntity.is2fa()) {
            return new UserDetailDto(userEntity, null);
        }
        final var builder = new QrData.Builder();
        final var qrData = builder.issuer("Planet Systems").label(userEntity.loginId()).secret(userEntity.secret())
                .algorithm(HashingAlgorithm.SHA1).digits(6).period(30).build();
        final var zxingPngQrGenerator = new ZxingPngQrGenerator();
        try {
            final byte[] bytes = zxingPngQrGenerator.generate(qrData);
            final var image = Utils.getDataUriForImage(bytes, zxingPngQrGenerator.getImageMimeType());
            return new UserDetailDto(userEntity, image);
        } catch (final QrGenerationException e) {
            throw new RuntimeException(e);
        }
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
     *            user dto
     * @param userInfoDto
     *            ユーザー情報
     * @return ユーザーID
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

    @Transactional
    public void updatePassword(final String id, final String newPassword, final String renewPassword) {
        Assert.notNull(id, "id must not be null");
        Assert.notNull(newPassword, "newPassword must not be null");
        Assert.notNull(renewPassword, "renewPassword must not be null");

        if (!StringUtils.equals(newPassword, renewPassword)) {
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

    /**
     * ユーザー削除
     *
     * @param id
     *            ユーザーID
     * @param userInfoDto
     *            ユーザー情報
     * @return 削除メッセージ
     */
    public String delete(final String id, final EarthlyUserInfoDto userInfoDto) {
        validateDeleteOperation(id, userInfoDto);

        final var user = userRepository.findByPrimaryKey(id).orElseThrow(() -> new BadRequestException(EWA4XX002));
        user.setUpdatedBy(userInfoDto.id());
        user.setIsDeleted(true);
        userRepository.update(user);
        return messageSource.getMessage(MessageKey.DELETE_SUCCESS, ArrayUtils.EMPTY_OBJECT_ARRAY, Locale.JAPAN);
    }

    /**
     * 削除操作の検証
     *
     * @param id
     *            ユーザーID
     * @param userInfoDto
     *            ユーザー情報
     * @throws BadRequestException
     *             操作ユーザーと削除ユーザーが同じ場合に発生
     */
    @VisibleForTesting
    void validateDeleteOperation(final String id, final EarthlyUserInfoDto userInfoDto) {
        Assert.notNull(id, "id must not be null");
        if (StringUtils.equals(id, userInfoDto.id())) {
            throw new BadRequestException(EWA4XX002);
        }
        validateDeletePermission(id, userInfoDto);
    }

    /**
     * 削除権限の検証
     *
     * @param id
     *            ユーザーID
     * @param userInfoDto
     *            ユーザー情報
     * @throws ForbiddenException
     *             削除権限を保持していない場合に発生
     */
    void validateDeletePermission(final String id, final EarthlyUserInfoDto userInfoDto) {

        if (userInfoDto.permissionEnumList().contains(PermissionEnum.EDIT_USER)) {
            return;
        }

        if (!userInfoDto.permissionEnumList().contains(PermissionEnum.EDIT_MY_COMPANY_BRANCH)) {
            return;
        }

        final var user = userRepository.findByPrimaryKey(id).orElseThrow(() -> new NotFoundException(EWA4XX002));
        if (StringUtils.equals(user.getCompanyId(), userInfoDto.company().id())) {
            return;
        }
        throw new ForbiddenException(EWA4XX008);
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
     * @param userInfoDto
     *            ユーザー情報
     * @return ロールページ
     */
    @Transactional
    public PageImpl<Role> findUnassignedRole(final String id, final String roleName, final Pageable pageable,
            final EarthlyUserInfoDto userInfoDto) {
        final var roleSearchResultDto = roleRepository.findUnassignedRoleByUserIdAndLikeName(id, roleName, pageable,
                userInfoDto.id(), userInfoDto.permissionEnumList());
        return new PageImpl<>(roleSearchResultDto.roleList(), pageable, roleSearchResultDto.total());
    }

    /**
     * ロール割り当て
     * 
     * @param id
     *            ユーザーID
     * @param assignRoleList
     *            割り当てるロールリスト
     * @param userInfoDto
     *            ユーザー情報
     */
    @Transactional
    public void assignRole(final String id, final List<String> assignRoleList, final EarthlyUserInfoDto userInfoDto) {

        validateAccessible(id, userInfoDto);
        validateAssignableRole(id, assignRoleList, userInfoDto);
        validateGrantingRoleDuplication(id, assignRoleList);

        for (final var roleId : assignRoleList) {
            final var userRole = new UserRole(null, id, roleId, LocalDateTime.now(ZoneOffset.UTC), userInfoDto.id());
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
     * @param userInfoDto
     *            ユーザー情報
     * @throws ForbiddenException
     *             ロールを割り当てられない場合に発生
     */
    @VisibleForTesting
    void validateAssignableRole(final String id, final List<String> assignRoleList,
            final EarthlyUserInfoDto userInfoDto) {
        final var roleSearchResultDto = roleRepository.findUnassignedRoleByUserIdAndLikeName(id, StringUtils.EMPTY,
                Pageable.ofSize(Integer.MAX_VALUE), userInfoDto.id(), userInfoDto.permissionEnumList());
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
}
