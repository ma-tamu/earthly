package jp.co.project.planets.earthly.webapp.service;

import com.google.common.annotations.VisibleForTesting;
import jp.co.project.planets.earthly.emuns.PermissionEnum;
import jp.co.project.planets.earthly.model.dto.UserSearchResultDto;
import jp.co.project.planets.earthly.model.entity.UserEntity;
import jp.co.project.planets.earthly.repository.UserRepository;
import jp.co.project.planets.earthly.webapp.exception.ForbiddenException;
import jp.co.project.planets.earthly.webapp.logic.UserLogic;
import jp.co.project.planets.earthly.webapp.model.dto.UserSearchDto;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static jp.co.project.planets.earthly.webapp.emuns.ErrorCode.*;

/**
 * user service
 */
@Service
public class UserService {

    private final UserLogic userLogic;

    private final UserRepository userRepository;

    /**
     * new instance user service
     *
     * @param userLogic
     *         user logic
     * @param userRepository
     *         user repository
     */
    public UserService(final UserLogic userLogic, final UserRepository userRepository) {
        this.userLogic = userLogic;
        this.userRepository = userRepository;
    }

    /**
     * 対象ユーザーを取得する。
     *
     * @param id
     *         ユーザーID
     * @param userInfoDto
     *         ユーザー情報
     * @return UserEntity
     */
    @Transactional
    public UserEntity getById(final String id, final EarthlyUserInfoDto userInfoDto) {

        validateAccessible(id, userInfoDto);

        return userLogic.getAccessibleEntity(id, userInfoDto);
    }

    /**
     * 対象ユーザーが閲覧できるか検証する
     *
     * @param id
     *         ユーザーID
     * @param userInfoDto
     *         ユーザー情報
     * @throws ForbiddenException
     *         対象ユーザー閲覧できない場合に発生
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

    @Transactional
    public UserSearchResultDto search(final UserSearchDto userSearchDto, final Pageable pageable,
            final EarthlyUserInfoDto userInfoDto) {
        return userRepository.findByLoginIdAndNameAndCompany(userSearchDto.loginId(), userSearchDto.name(),
                userSearchDto.company(), pageable, userInfoDto.permissionEnumList(), userInfoDto.id());
    }
}
