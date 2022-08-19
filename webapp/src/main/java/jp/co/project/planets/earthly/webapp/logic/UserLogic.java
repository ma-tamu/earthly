package jp.co.project.planets.earthly.webapp.logic;

import jp.co.project.planets.earthly.model.entity.UserEntity;
import jp.co.project.planets.earthly.repository.UserRepository;
import jp.co.project.planets.earthly.webapp.exception.NotFoundException;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import org.springframework.stereotype.Component;

import static jp.co.project.planets.earthly.webapp.emuns.ErrorCode.*;

/**
 * user logic
 */
@Component
public class UserLogic {

    private final UserRepository userRepository;

    /**
     * new instance user logic
     *
     * @param userRepository
     *         user repository
     */
    public UserLogic(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 閲覧可能なユーザーを取得する。
     *
     * @param id
     *         ユーザーID
     * @param userInfoDto
     *         認証したユーザー情報
     * @return UserEntity
     * @throws NotFoundException
     *         対象ユーザーが取得できない場合に発生
     */
    public UserEntity getAccessibleEntity(final String id, final EarthlyUserInfoDto userInfoDto) {
        return userRepository.findAccessibleByPrimaryKey(id, userInfoDto.permissionEnumList(),
                userInfoDto.id()).orElseThrow(
                () -> new NotFoundException(String.format("not found user user=%s.", id), EWA4XX002));
    }
}
