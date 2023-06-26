package jp.co.project.planets.earthly.common.logic;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.annotations.VisibleForTesting;

import dev.samstevens.totp.recovery.RecoveryCodeGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import jp.co.project.planets.earthly.common.model.dto.UserDto;
import jp.co.project.planets.earthly.schema.db.entity.PasswordToken;
import jp.co.project.planets.earthly.schema.db.entity.RecoveryCode;
import jp.co.project.planets.earthly.schema.db.entity.User;
import jp.co.project.planets.earthly.schema.emuns.PermissionEnum;
import jp.co.project.planets.earthly.schema.model.entity.UserEntity;
import jp.co.project.planets.earthly.schema.repository.PasswordTokenRepository;
import jp.co.project.planets.earthly.schema.repository.RecoveryCodeRepository;
import jp.co.project.planets.earthly.schema.repository.UserRepository;

/**
 * user logic
 */
@Component
public class UserLogic {

    private final RoleLogic roleLogic;
    private final MailLogic mailLogic;
    private final CryptoLogic cryptoLogic;
    private final UserRepository userRepository;
    private final PasswordTokenRepository passwordTokenRepository;
    private final RecoveryCodeRepository recoveryCodeRepository;

    private static final Logger log = LoggerFactory.getLogger(UserLogic.class);

    public UserLogic(final RoleLogic roleLogic, final MailLogic mailLogic, final CryptoLogic cryptoLogic,
            final UserRepository userRepository, final PasswordTokenRepository passwordTokenRepository,
            final RecoveryCodeRepository recoveryCodeRepository) {
        this.roleLogic = roleLogic;
        this.mailLogic = mailLogic;
        this.cryptoLogic = cryptoLogic;
        this.userRepository = userRepository;
        this.passwordTokenRepository = passwordTokenRepository;
        this.recoveryCodeRepository = recoveryCodeRepository;
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
        final var user = generateUser(userDto, operationUserId);
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
        insertRecoveryCode(id);
        insertPasswordToken(id);
        //        mailLogic.postUserCreationNotification(id);
        return userOptional;
    }

    /**
     * ユーザーエンティティ生成
     * 
     * @param userDto
     *            user entry dto
     * @param operationUserId
     *            操作ユーザーID
     * @return User
     */
    @VisibleForTesting
    User generateUser(final UserDto userDto, final String operationUserId) {
        final var user = userDto.toEntity();
        // MFAコード発行用のシークレットを発行
        // https://github.com/samdjstevens/java-totp
        final var secretGenerator = new DefaultSecretGenerator();
        final var secret = secretGenerator.generate();
        user.setSecret(secret);
        user.setCreatedBy(operationUserId);
        user.setUpdatedBy(operationUserId);
        return user;
    }

    /**
     * insert recovery code
     * 
     * @param id
     *            user id
     */
    void insertRecoveryCode(final String id) {
        final var recoveryCodeGenerator = new RecoveryCodeGenerator();
        final var recoveryCodes = recoveryCodeGenerator.generateCodes(6);
        for (final var recoveryCode : recoveryCodes) {
            final var entity = new RecoveryCode(null, id, recoveryCode, false);
            recoveryCodeRepository.insert(entity);
        }
    }

    /**
     * パスワードトークンを登録
     * 
     * @param id
     *            ユーザーID
     */
    private void insertPasswordToken(final String id) {
        final var expire = LocalDateTime.now(Clock.systemUTC()).plusDays(1);
        final var token = cryptoLogic.encodeSHA256(expire.toString());
        final var passwordToken = new PasswordToken(null, id, token, expire);
        passwordTokenRepository.insert(passwordToken);
    }

    /**
     * ユーザー更新
     * 
     * @param user
     *            ユーザーエンティティ
     * @param userDto
     *            ユーザーDTO
     * @param operateUserId
     *            操作ユーザーID
     */
    public void update(final User user, final UserDto userDto, final String operateUserId) {
        user.setName(userDto.name());
        user.setMail(userDto.mail());
        final var lockout = StringUtils.equals(user.getId(), operateUserId) ? user.getLockout() : userDto.lockout();
        user.setLockout(lockout);
        user.setTwoFactorAuthentication(BooleanUtils.isTrue(userDto.is2fa()));
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
