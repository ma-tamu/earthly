package jp.co.project.planets.earthly.webapp.service;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.annotations.VisibleForTesting;

import jp.co.project.planets.earthly.common.logic.OAuthClientLogic;
import jp.co.project.planets.earthly.schema.db.entity.OauthClient;
import jp.co.project.planets.earthly.schema.emuns.PermissionEnum;
import jp.co.project.planets.earthly.schema.repository.OAuthClientManagementRepository;
import jp.co.project.planets.earthly.schema.repository.OAuthClientRepository;
import jp.co.project.planets.earthly.webapp.emuns.ErrorCode;
import jp.co.project.planets.earthly.webapp.exception.ForbiddenException;
import jp.co.project.planets.earthly.webapp.exception.NotFoundException;
import jp.co.project.planets.earthly.webapp.model.dto.OAuthClientDetailDto;
import jp.co.project.planets.earthly.webapp.model.dto.OAuthClientEditDto;
import jp.co.project.planets.earthly.webapp.model.dto.OAuthClientEntryDto;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;

/**
 * OAuthクライアントサービス
 */
@Service
public class OAuthClientService {

    private final OAuthClientLogic oauthClientLogic;
    private final OAuthClientRepository oauthClientRepository;

    private final OAuthClientManagementRepository oauthClientManagementRepository;

    public OAuthClientService(final OAuthClientLogic oauthClientLogic,
            final OAuthClientRepository oauthClientRepository,
            final OAuthClientManagementRepository oauthClientManagementRepository) {
        this.oauthClientLogic = oauthClientLogic;
        this.oauthClientRepository = oauthClientRepository;
        this.oauthClientManagementRepository = oauthClientManagementRepository;
    }

    /**
     * OAuthクライアント検索
     *
     * @param name
     *            OAuthクライアント名
     * @param pageable
     *            ページャー
     * @param userInfoDto
     *            ユーザー情報
     * @return OAuthクライアントリスト
     */
    @Transactional
    public PageImpl<OauthClient> search(final String name, final Pageable pageable,
            final EarthlyUserInfoDto userInfoDto) {

        validateAccessibleClient(userInfoDto);

        final var oauthClientSearchResultDto = oauthClientRepository.findByName(name, pageable,
                userInfoDto.permissionEnumList(), userInfoDto.id());
        return new PageImpl<>(oauthClientSearchResultDto.oauthClientList(), pageable,
                oauthClientSearchResultDto.total());
    }

    /**
     * 閲覧できるOAuthクライアントの検証
     *
     * @param userInfoDto
     *            ユーザー情報
     */
    @VisibleForTesting
    void validateAccessibleClient(final EarthlyUserInfoDto userInfoDto) {

        final var oauthClientList = oauthClientRepository.findByAccessible(userInfoDto.permissionEnumList(),
                userInfoDto.id());
        if (CollectionUtils.isEmpty(oauthClientList)) {
            throw new ForbiddenException(ErrorCode.EWA4XX016);
        }
    }

    /**
     * OAuthクライアント取得
     * 
     * @param id
     *            OAuthクライアントID
     * @param userInfoDto
     *            ユーザー情報
     * @return OAuthクライアント
     */
    public OAuthClientDetailDto get(final String id, final EarthlyUserInfoDto userInfoDto) {

        final var permissionEnumList = userInfoDto.permissionEnumList();
        final var operationUserId = userInfoDto.id();
        final boolean canAccessibleClient = oauthClientLogic.canAccessibleClient(id, permissionEnumList,
                operationUserId);
        if (!canAccessibleClient) {
            throw new ForbiddenException(ErrorCode.EWA4XX016);
        }

        final var client = oauthClientRepository.findAccessibleById(id, permissionEnumList, operationUserId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EWA4XX018));
        final boolean canEditableClient = oauthClientLogic.canEditableClient(id, permissionEnumList, operationUserId);
        final var redirectUrlPage = new PageImpl<>(client.redirectUrls());
        final var logoutRedirectUrlPage = new PageImpl<>(client.logoutRedirectUrls());
        final var userPage = new PageImpl<>(client.managementUserList());
        return new OAuthClientDetailDto(client, redirectUrlPage, logoutRedirectUrlPage, userPage, canEditableClient);
    }

    /**
     * OAuthクライアント登録パーミッション検証
     *
     * @param userInfoDto
     *            ユーザー情報
     * @throws ForbiddenException
     *             add_oauth_clientを保持していない場合に発生
     */
    public void validateEntryPermission(final EarthlyUserInfoDto userInfoDto) {
        // OAuthクライアント登録パーミッションを保持していない場合は、エラーとする。
        if (!userInfoDto.permissionEnumList().contains(PermissionEnum.ADD_OAUTH_CLIENT)) {
            throw new ForbiddenException(ErrorCode.EWA4XX017);
        }
    }

    /**
     * OAuthクライアント登録検証
     *
     * @param oauthClientEntryDto
     *            OAuthクライアント登録DTO
     * @param userInfoDto
     *            ユーザー情報
     */
    public void validateEntryOperation(final OAuthClientEntryDto oauthClientEntryDto,
            final EarthlyUserInfoDto userInfoDto) {
        validateEntryPermission(userInfoDto);
    }

    /**
     * OAuthクライアント登録
     *
     * @param oauthClientEntryDto
     *            OAuthクライアント登録DTO
     * @param userInfoDto
     *            ユーザー情報
     * @return OAuthクライアントID
     */
    @Transactional
    public String create(final OAuthClientEntryDto oauthClientEntryDto,
            final EarthlyUserInfoDto userInfoDto) {
        validateEntryPermission(userInfoDto);
        return oauthClientLogic.create(oauthClientEntryDto.name(), oauthClientEntryDto.scopes(), userInfoDto.id());
    }

    public void validateEditPermission(final String id, final OAuthClientEditDto oauthClientEditDto,
            final EarthlyUserInfoDto userInfoDto) {

        final var permissionEnumList = userInfoDto.permissionEnumList();
        if (!permissionEnumList.contains(PermissionEnum.EDIT_OAUTH_CLIENT)) {
            throw new ForbiddenException(ErrorCode.EWA4XX019);
        }

        final boolean canEditableClient = oauthClientLogic.canEditableClient(id, permissionEnumList,
                userInfoDto.id());
        if (!canEditableClient) {
            throw new ForbiddenException(ErrorCode.EWA4XX019);
        }
    }
}
