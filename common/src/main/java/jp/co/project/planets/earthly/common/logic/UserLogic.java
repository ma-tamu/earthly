package jp.co.project.planets.earthly.common.logic;

import jp.co.project.planets.earthly.common.model.dto.UserEntryDto;
import jp.co.project.planets.earthly.db.entity.User;
import jp.co.project.planets.earthly.emuns.PermissionEnum;
import jp.co.project.planets.earthly.model.entity.UserEntity;
import jp.co.project.planets.earthly.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * user logic
 */
@Component
public class UserLogic {

    private final UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(UserLogic.class);

    public UserLogic(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 閲覧可能なユーザーを取得する。
     *
     * @param id
     *         ユーザーID
     * @param permissionEnumList
     *         パーミッションリスト
     * @param operationUserId
     *         操作ユーザーID
     * @return UserEntity
     */
    public Optional<UserEntity> getAccessibleEntity(final String id, final List<PermissionEnum> permissionEnumList,
            final String operationUserId) {
        return userRepository.findAccessibleByPrimaryKey(id, permissionEnumList, operationUserId);
    }

    /**
     * create user
     *
     * @param userEntryDto
     *         user entry dto
     * @param operationUserId
     *         操作ユーザーID
     * @return User
     */
    public Optional<User> create(final UserEntryDto userEntryDto, final String operationUserId) {
        final var user = userEntryDto.toEntity();
        user.setCreatedBy(operationUserId);
        user.setUpdatedBy(operationUserId);
        final int result = userRepository.insert(user);
        if (result < 1) {
            log.error("ユーザー登録に失敗しました。 loginId:{}", user.getLoginId());
            return Optional.empty();
        }
        // TODO メール通知する。
        return userRepository.findByLoginId(userEntryDto.loginId());
    }
}
