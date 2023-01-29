package jp.co.project.planets.earthly.common.logic;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jp.co.project.planets.earthly.common.model.dto.UserDto;
import jp.co.project.planets.earthly.db.entity.User;
import jp.co.project.planets.earthly.emuns.PermissionEnum;
import jp.co.project.planets.earthly.model.entity.UserEntity;
import jp.co.project.planets.earthly.repository.UserRepository;

/**
 * user logic
 */
@Component
public class UserLogic {

    private final RoleLogic roleLogic;
    private final MailLogic mailLogic;
    private final UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(UserLogic.class);

    public UserLogic(final RoleLogic roleLogic, final MailLogic mailLogic, final UserRepository userRepository) {
        this.roleLogic = roleLogic;
        this.mailLogic = mailLogic;
        this.userRepository = userRepository;
    }

    /**
     * 閲覧可能なユーザーを取得する。
     *
     * @param id
     *            ユーザーID
     * @param permissionEnumList
     *            パーミッションリスト
     * @param operationUserId
     *            操作ユーザーID
     * @return UserEntity
     */
    public Optional<UserEntity> getAccessibleEntity(final String id, final List<PermissionEnum> permissionEnumList,
            final String operationUserId) {
        return userRepository.findAccessibleByPrimaryKey(id, permissionEnumList, operationUserId);
    }

    /**
     * create user
     *
     * @param userDto
     *            user entry dto
     * @param operationUserId
     *            操作ユーザーID
     * @return User
     */
    public Optional<User> create(final UserDto userDto, final String operationUserId) {
        final var user = userDto.toEntity();
        user.setCreatedBy(operationUserId);
        user.setUpdatedBy(operationUserId);
        final int result = userRepository.insert(user);
        if (result < 1) {
            log.error("ユーザー登録に失敗しました。 loginId:{}", user.getLoginId());
            return Optional.empty();
        }
        final var userOptional = userRepository.findByLoginId(userDto.loginId());
        if (userOptional.isEmpty()) {
            log.error("user not found? loginId:{}", userDto.loginId());
        }
        final var createdUser = userOptional.get();
        final var id = createdUser.getId();
        roleLogic.grantDefaultRole(id, operationUserId);
        mailLogic.postUserCreationNotification(id);

        return userOptional;
    }

    public void update(final User user, final UserDto userDto, final String operateUserId) {
        user.setName(userDto.name());
        user.setMail(userDto.mail());
        final var lockout = StringUtils.equals(user.getId(), operateUserId) ? user.getLockout() : userDto.lockout();
        user.setLockout(lockout);
        user.setLanguage(userDto.language());
        user.setTimezone(userDto.timezone());
        user.setCompanyId(userDto.company());
        user.setUpdatedAt(LocalDateTime.now(ZoneOffset.UTC));
        user.setUpdatedBy(operateUserId);
        final int result = userRepository.update(user);
        if (result < 1) {
            throw new RuntimeException("ユーザー更新に失敗しました。");
        }
    }
}
