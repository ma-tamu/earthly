package jp.co.project.planets.earthly.webapp.service;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.annotations.VisibleForTesting;

import jp.co.project.planets.earthly.common.logic.OAuthClientLogic;
import jp.co.project.planets.earthly.schema.db.entity.OauthClient;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientRedirectUrl;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientScope;
import jp.co.project.planets.earthly.schema.db.entity.Scope;
import jp.co.project.planets.earthly.schema.emuns.PermissionEnum;
import jp.co.project.planets.earthly.schema.repository.OAuthClientManagementRepository;
import jp.co.project.planets.earthly.schema.repository.OAuthClientRedirectUrlRepository;
import jp.co.project.planets.earthly.schema.repository.OAuthClientRepository;
import jp.co.project.planets.earthly.schema.repository.OAuthClientScopeRepository;
import jp.co.project.planets.earthly.schema.repository.ScopeRepository;
import jp.co.project.planets.earthly.webapp.constant.MessageKey;
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
    private final ScopeRepository scopeRepository;
    private final OAuthClientScopeRepository oauthClientScopeRepository;
    private final OAuthClientRedirectUrlRepository oauthClientRedirectUrlRepository;
    private final OAuthClientManagementRepository oauthClientManagementRepository;

    private final MessageSource messageSource;

    public OAuthClientService(final OAuthClientLogic oauthClientLogic,
            final OAuthClientRepository oauthClientRepository, final ScopeRepository scopeRepository,
            final OAuthClientScopeRepository oauthClientScopeRepository,
            final OAuthClientRedirectUrlRepository oauthClientRedirectUrlRepository,
            final OAuthClientManagementRepository oauthClientManagementRepository, final MessageSource messageSource) {
        this.oauthClientLogic = oauthClientLogic;
        this.oauthClientRepository = oauthClientRepository;
        this.scopeRepository = scopeRepository;
        this.oauthClientScopeRepository = oauthClientScopeRepository;
        this.oauthClientRedirectUrlRepository = oauthClientRedirectUrlRepository;
        this.oauthClientManagementRepository = oauthClientManagementRepository;
        this.messageSource = messageSource;
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

    /**
     * OAuthクライアント編集
     * 
     * @param id
     *            OAuthクライアントID
     * @param oauthClientEditDto
     *            OAuthクライアント編集DTO
     * @param userInfoDto
     *            ユーザー情報
     * @return 編集メッセージ
     */
    @Transactional
    public String update(final String id, final OAuthClientEditDto oauthClientEditDto,
            final EarthlyUserInfoDto userInfoDto) {

        validateEditPermission(id, oauthClientEditDto, userInfoDto);

        final var oauthClient = oauthClientRepository.findByPrimaryKey(id);
        oauthClient.setName(oauthClientEditDto.name());
        oauthClient.setUpdatedBy(userInfoDto.id());
        oauthClientRepository.update(oauthClient);

        updateScope(id, oauthClientEditDto.scopes(), userInfoDto);

        return messageSource.getMessage(MessageKey.UPDATE_SUCCESS, ArrayUtils.EMPTY_OBJECT_ARRAY, Locale.JAPAN);
    }

    /**
     * update scope
     * 
     * @param id
     *            oauth client id
     * @param scopes
     *            edit scope
     * @param userInfoDto
     *            ユーザー情報
     */
    @VisibleForTesting
    void updateScope(final String id, final List<String> scopes, final EarthlyUserInfoDto userInfoDto) {

        // 編集前と編集後のスコープがすべて一致するか確認を行い、１件でも異なる場合は、delete/insertを行う。
        final boolean isSameAllScope = isSameAllScope(id, scopes);
        if (isSameAllScope) {
            return;
        }

        oauthClientScopeRepository.deleteByClientId(id);
        for (final String scope : scopes) {
            final var oauthClientScope = new OauthClientScope(null, id, scope);
            oauthClientScopeRepository.insert(oauthClientScope);
        }
    }

    /**
     * 登録済みのスコープと編集したスコープがすべて一致するか
     * 
     * @param id
     *            OAuthクライアントID
     * @param scopes
     *            編集したスコープ
     * @return true:差分なし false:差分あり
     */
    @VisibleForTesting
    boolean isSameAllScope(final String id, final List<String> scopes) {
        final var scopeList = scopeRepository.findByClientId(id);
        final var scopeIdList = scopeList.stream().map(Scope::getId).toList();

        // 登録済みのスコープすべて一致しない場合は、差分あるとみなしfalseを返す。
        final long alreadyScopeCount = scopeIdList.stream().filter(it -> !scopes.contains(it)).count();
        if (alreadyScopeCount != 0) {
            return false;
        }
        // 更新対象のスコープが1件でも存在する場合に、差分ありとする。
        final var updateScopeCount = scopes.stream().filter(it -> !scopeIdList.contains(it)).count();
        return updateScopeCount == 0;
    }

    @Transactional
    public PageImpl<OauthClientRedirectUrl> searchRedirectUrl(final String id, final String redirectUtl,
            final Pageable pageable, final EarthlyUserInfoDto userInfoDto) {

        return new PageImpl<>(Collections.emptyList());
    }
}
