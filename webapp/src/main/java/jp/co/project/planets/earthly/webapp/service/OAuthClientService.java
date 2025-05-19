package jp.co.project.planets.earthly.webapp.service;

import static jp.co.project.planets.earthly.common.constant.PageBoundary.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.annotations.VisibleForTesting;

import jp.co.project.planets.earthly.common.logic.OAuthClientLogic;
import jp.co.project.planets.earthly.core.account.Account;
import jp.co.project.planets.earthly.schema.db.entity.LogoutRedirectUrl;
import jp.co.project.planets.earthly.schema.db.entity.OauthClient;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientManagement;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientRedirectUrl;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientScope;
import jp.co.project.planets.earthly.schema.db.entity.Scope;
import jp.co.project.planets.earthly.schema.emuns.PermissionEnum;
import jp.co.project.planets.earthly.schema.model.entity.OAuthClientManagementUserEntity;
import jp.co.project.planets.earthly.schema.repository.LogoutRedirectRepository;
import jp.co.project.planets.earthly.schema.repository.OAuthClientManagementRepository;
import jp.co.project.planets.earthly.schema.repository.OAuthClientRedirectUrlRepository;
import jp.co.project.planets.earthly.schema.repository.OAuthClientRepository;
import jp.co.project.planets.earthly.schema.repository.OAuthClientScopeRepository;
import jp.co.project.planets.earthly.schema.repository.ScopeRepository;
import jp.co.project.planets.earthly.schema.repository.UserRepository;
import jp.co.project.planets.earthly.webapp.constant.MessageKey;
import jp.co.project.planets.earthly.webapp.emuns.ErrorCode;
import jp.co.project.planets.earthly.webapp.exception.BadRequestException;
import jp.co.project.planets.earthly.webapp.exception.ForbiddenException;
import jp.co.project.planets.earthly.webapp.exception.NotFoundException;
import jp.co.project.planets.earthly.webapp.model.dto.OAuthClientDetailDto;
import jp.co.project.planets.earthly.webapp.model.dto.OAuthClientEditDto;
import jp.co.project.planets.earthly.webapp.model.dto.OAuthClientEntryDto;

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
    private final LogoutRedirectRepository logoutRedirectRepository;
    private final UserRepository userRepository;

    public OAuthClientService(final OAuthClientLogic oauthClientLogic,
        final OAuthClientRepository oauthClientRepository, final ScopeRepository scopeRepository,
        final OAuthClientScopeRepository oauthClientScopeRepository,
        final OAuthClientRedirectUrlRepository oauthClientRedirectUrlRepository,
        final OAuthClientManagementRepository oauthClientManagementRepository, final MessageSource messageSource,
        final LogoutRedirectRepository logoutRedirectRepository, final UserRepository userRepository) {
        this.oauthClientLogic = oauthClientLogic;
        this.oauthClientRepository = oauthClientRepository;
        this.scopeRepository = scopeRepository;
        this.oauthClientScopeRepository = oauthClientScopeRepository;
        this.oauthClientRedirectUrlRepository = oauthClientRedirectUrlRepository;
        this.oauthClientManagementRepository = oauthClientManagementRepository;
        this.messageSource = messageSource;
        this.logoutRedirectRepository = logoutRedirectRepository;
        this.userRepository = userRepository;
    }

    /**
     * OAuthクライアント検索
     *
     * @param name
     *            OAuthクライアント名
     * @param pageable
     *            ページャー
     * @param account
     *            ユーザー情報
     * @return OAuthクライアントリスト
     */
    @Transactional
    public PageImpl<OauthClient> search(final String name, final Pageable pageable,
        final Account account) {

        validateAccessibleClient(account);

        final var oauthClientSearchResultDto = oauthClientRepository.findByName(name, pageable, account);
        return new PageImpl<>(oauthClientSearchResultDto.oauthClientList(), pageable,
                oauthClientSearchResultDto.total());
    }

    /**
     * 閲覧できるOAuthクライアントの検証
     *
     * @param account
     *            ユーザー情報
     */
    @VisibleForTesting
    void validateAccessibleClient(final Account account) {

        final var oauthClientList = oauthClientRepository.findByAccessible(account);
        if (CollectionUtils.isEmpty(oauthClientList)) {
            throw new ForbiddenException(ErrorCode.EWA4XX016);
        }
    }

    /**
     * OAuthクライアント取得
     *
     * @param id
     *            OAuthクライアントID
     * @param account
     *            ユーザー情報
     * @return OAuthクライアント
     */
    public OAuthClientDetailDto get(final String id, final Account account) {

        final boolean canAccessibleClient = oauthClientLogic.canAccessibleClient(id, account);
        if (!canAccessibleClient) {
            throw new ForbiddenException(ErrorCode.EWA4XX016);
        }

        final var client = oauthClientRepository.findAccessibleById(id, account)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EWA4XX018));
        final boolean canEditableClient = oauthClientLogic.canEditableClient(id, account);
        final var pageable = PageRequest.of(0, DEFAULT_PAGE_SIZE);

        final var oauthClientRedirectUriSearchResultDto = oauthClientRedirectUrlRepository.findByClientRedirectUrl(id,
                null, pageable, account);
        final var redirectUrlPage = new PageImpl<>(oauthClientRedirectUriSearchResultDto.oauthClientRedirectUriList(),
                pageable, oauthClientRedirectUriSearchResultDto.total());
        final var oauthClientLogoutRedirectUriSearchResultDto = logoutRedirectRepository.findByClientRedirectUrl(id,
                null, pageable, account);
        final var logoutRedirectUrlPage = new PageImpl<>(
                oauthClientLogoutRedirectUriSearchResultDto.oauthClientLogoutRedirectUriList(), pageable,
                oauthClientLogoutRedirectUriSearchResultDto.total());
        final var oauthClientManagementUserSearchResultDto = oauthClientManagementRepository
                .findAccessibleUnassignedUserByAnyKeyword(id, null, null, null, pageable, account);
        final var userPage = new PageImpl<>(oauthClientManagementUserSearchResultDto.oauthClientManagementUserList(),
                pageable, oauthClientManagementUserSearchResultDto.total());
        return new OAuthClientDetailDto(client, redirectUrlPage, logoutRedirectUrlPage, userPage, canEditableClient);
    }

    /**
     * OAuthクライアント登録パーミッション検証
     *
     * @param account
     *            ユーザー情報
     * @throws ForbiddenException
     *             add_oauth_clientを保持していない場合に発生
     */
    public void validateEntryPermission(final Account account) {
        // OAuthクライアント登録パーミッションを保持していない場合は、エラーとする。
        if (!account.permissions().contains(PermissionEnum.ADD_OAUTH_CLIENT)) {
            throw new ForbiddenException(ErrorCode.EWA4XX017);
        }
    }

    /**
     * OAuthクライアント登録検証
     *
     * @param oauthClientEntryDto
     *            OAuthクライアント登録DTO
     * @param account
     *            ユーザー情報
     */
    public void validateEntryOperation(final OAuthClientEntryDto oauthClientEntryDto,
        final Account account) {
        validateEntryPermission(account);
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
        final Account account) {
        validateEntryPermission(account);
        return oauthClientLogic.create(oauthClientEntryDto.name(), oauthClientEntryDto.scopes(), account);
    }

    /**
     * OAuthクライアントの編集権限検証
     * 
     * @param id
     *            OAuthクライアントID
     * @param account
     *            ユーザー情報
     */
    public void validateEditPermission(final String id, final Account account) {

        if (!account.permissions().contains(PermissionEnum.EDIT_OAUTH_CLIENT)) {
            throw new ForbiddenException(ErrorCode.EWA4XX019);
        }

        final boolean canEditableClient = oauthClientLogic.canEditableClient(id, account);
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
        final Account account) {

        validateEditPermission(id, account);

        final var oauthClient = oauthClientRepository.findByPrimaryKey(id);
        oauthClient.setName(oauthClientEditDto.name());
        oauthClient.setUpdatedBy(account.id());
        oauthClientRepository.update(oauthClient);

        updateScope(id, oauthClientEditDto.scopes(), account);

        return messageSource.getMessage(MessageKey.UPDATE_SUCCESS, ArrayUtils.EMPTY_OBJECT_ARRAY, Locale.JAPAN);
    }

    /**
     * update scope
     * 
     * @param id
     *            oauth client id
     * @param scopes
     *            edit scope
     * @param account
     *            ユーザー情報
     */
    @VisibleForTesting
    void updateScope(final String id, final List<String> scopes, final Account account) {

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

    /**
     * OAuthクライアントリダイレクトURL検索
     * 
     * @param id
     *            OAuthクライアントID
     * @param redirectUtl
     *            リダイレクトURL
     * @param pageable
     *            ページャー
     * @param account
     *            ユーザー情報
     * @return OAuthクライアントリダイレクトURLページ
     */
    @Transactional
    public PageImpl<OauthClientRedirectUrl> searchRedirectUrl(final String id, final String redirectUtl,
        final Pageable pageable, final Account account) {
        final var oauthClientRedirectUriSearchResultDto = oauthClientRedirectUrlRepository.findByClientRedirectUrl(id,
                redirectUtl, pageable, account);
        return new PageImpl<>(oauthClientRedirectUriSearchResultDto.oauthClientRedirectUriList(), pageable,
                oauthClientRedirectUriSearchResultDto.total());
    }

    /**
     * OAuthクライアントリダイレクトURL追加
     * 
     * @param id
     *            OAuthクライアントID
     * @param redirectUrl
     *            リダイレクトURL
     * @param account
     *            ユーザー情報
     */
    @Transactional
    public void addRedirectUrl(final String id, final String redirectUrl, final Account account) {

        validateEditPermission(id, account);

        final var currentDateTime = LocalDateTime.now();
        final var oauthClientRedirectUrl = new OauthClientRedirectUrl(null, id, redirectUrl, currentDateTime,
                account.id(), currentDateTime, account.id(), false);
        oauthClientRedirectUrlRepository.insert(oauthClientRedirectUrl);
    }

    /**
     * リダイレクトURI削除
     * 
     * @param id
     *            OAuthクライアントID
     * @param redirectUrlIdList
     *            削除対象のリダイレクトURI IDリスト
     * @param account
     *            ユーザー情報
     * @throws BadRequestException
     *             存在しないリダイレクトURIがある場合に発生
     */
    @Transactional
    public void removeRedirectUri(final String id, final List<String> redirectUrlIdList,
        final Account account) {

        validateEditPermission(id, account);

        final var redirectUriList = oauthClientRedirectUrlRepository.findByClientIdAndRedirectUris(id,
                redirectUrlIdList, account);
        if (redirectUriList.size() != redirectUrlIdList.size()) {
            throw new BadRequestException(ErrorCode.EWA4XX021);
        }

        for (final var redirectUri : redirectUriList) {
            oauthClientRedirectUrlRepository.delete(redirectUri.getId());
        }
    }

    /**
     * OAuthクライアントログアウトリダイレクトURL検索
     *
     * @param id
     *            OAuthクライアントID
     * @param logoutRedirectUtl
     *            ログアウトリダイレクトURL
     * @param pageable
     *            ページャー
     * @param account
     *            ユーザー情報
     * @return OAuthクライアントログアウトリダイレクトURLページ
     */
    @Transactional
    public PageImpl<LogoutRedirectUrl> searchLogoutRedirectUrl(final String id, final String logoutRedirectUtl,
        final Pageable pageable, final Account account) {
        final var oauthClientLogoutRedirectUriSearchResultDto = logoutRedirectRepository.findByClientRedirectUrl(id,
                logoutRedirectUtl, pageable, account);
        return new PageImpl<>(oauthClientLogoutRedirectUriSearchResultDto.oauthClientLogoutRedirectUriList(), pageable,
                oauthClientLogoutRedirectUriSearchResultDto.total());
    }

    /**
     * OAuthクライアントログアウトリダイレクトURL追加
     *
     * @param id
     *            OAuthクライアントID
     * @param redirectUrl
     *            リダイレクトURL
     * @param account
     *            ユーザー情報
     */
    @Transactional
    public void addLogoutRedirectUrl(final String id, final String redirectUrl, final Account account) {
        validateEditPermission(id, account);

        final var currentDateTime = LocalDateTime.now();
        final var oauthClientLogoutRedirectUri = new LogoutRedirectUrl(null, id, redirectUrl, currentDateTime,
                account.id(), currentDateTime, account.id(), false);
        logoutRedirectRepository.insert(oauthClientLogoutRedirectUri);

    }

    /**
     * ログアウトリダイレクトURI削除
     *
     * @param id
     *            OAuthクライアントID
     * @param redirectUrlIdList
     *            削除対象のログアウトリダイレクトURI IDリスト
     * @param account
     *            ユーザー情報
     * @throws BadRequestException
     *             存在しないリダイレクトURIがある場合に発生
     */
    @Transactional
    public void removeLogoutRedirectUri(final String id, final List<String> redirectUrlIdList,
        final Account account) {

        validateEditPermission(id, account);

        final var logoutRedirectUriList = logoutRedirectRepository.findByClientIdAndRedirectUris(id,
                redirectUrlIdList, account);
        if (logoutRedirectUriList.size() != redirectUrlIdList.size()) {
            throw new BadRequestException(ErrorCode.EWA4XX021);
        }

        for (final var redirectUri : logoutRedirectUriList) {
            logoutRedirectRepository.delete(redirectUri.getId());
        }
    }

    /**
     * OAuthクライアント管理者検索
     * 
     * @param id
     *            OAuthクライアントID
     * @param userName
     *            ユーザー名
     * @param companyName
     *            会社名
     * @param pageable
     *            ページャー
     * @param account
     *            ユーザー情報
     * @return OAuthクライアント管理者リスト
     */
    @Transactional
    public PageImpl<OAuthClientManagementUserEntity> searchManagementUser(final String id, final String userName,
        final String companyName, final Pageable pageable, final Account account) {
        final var oauthClientManagementUserList = oauthClientManagementRepository.findByAccessibleClientIdAndUserId(id,
                userName, companyName, pageable, account);
        return new PageImpl<>(oauthClientManagementUserList);
    }

    /**
     * OAuthクライアント管理者未割り当てユーザー検索
     * 
     * @param id
     *            OAuthクライアントID
     * @param longinId
     *            ログインID
     * @param userName
     *            ユーザー名
     * @param companyName
     *            所属会社名
     * @param pageable
     *            ページャー
     * @param userInfoDto
     *            ユーザー情報
     * @return OAuthクライアント管理者未割り当てユーザーリスト
     */
    @Transactional
    public PageImpl<OAuthClientManagementUserEntity> searchNotAssignUserUser(final String id, final String longinId,
        final String userName, final String companyName, final Pageable pageable, final Account account) {
        final var oauthClientManagementUserSearchResultDto = oauthClientManagementRepository
                .findAccessibleUnassignedUserByAnyKeyword(id, longinId, userName, companyName, pageable, account);
        return new PageImpl<>(oauthClientManagementUserSearchResultDto.oauthClientManagementUserList(), pageable,
                oauthClientManagementUserSearchResultDto.total());
    }

    /**
     * OAuthクライアント管理者割り当て
     * 
     * @param id
     *            OAuthクライアントID
     * @param userIdList
     *            ユーザーIDリスト
     * @param account
     *            ユーザー情報
     * @throws BadRequestException
     *             割り当て対象のユーザーが既に割り当たっている場合に発生
     */
    @Transactional
    public void assignUser(final String id, final List<String> userIdList, final Account account) {
        validateEditPermission(id, account);

        // 割り当て対象のユーザーが既に管理者の場合は、エラーを返す。
        final var oauthClientManagementList = oauthClientManagementRepository.findAccessibleByClientIdAndInUserId(id,
                userIdList, account);
        if (CollectionUtils.isNotEmpty(oauthClientManagementList)) {
            throw new BadRequestException(ErrorCode.EWA4XX022);
        }

        for (final var userId : userIdList) {
            final var oauthClientManagement = new OauthClientManagement(null, id, userId, LocalDateTime.now(),
                    account.id());
            oauthClientManagementRepository.insert(oauthClientManagement);
        }
    }

    /**
     * OAuthクライアント管理者解除
     * 
     * @param id
     *            OAuthクライアントID
     * @param userIdList
     *            ユーザーIDリスト
     * @param account
     *            ユーザー情報
     * @throws BadRequestException
     *             対象ユーザーが閲覧できない又は既にOAuthクライアント管理者でない場合に発生
     */
    @Transactional
    public void unassignUser(final String id, final List<String> userIdList, final Account account) {

        validateEditPermission(id, account);

        final var userList = userRepository.findByPrimaryKeysAccessibly(userIdList, account);
        if (userList.size() != userIdList.size()) {
            throw new BadRequestException(ErrorCode.EWA4XX023);
        }

        final var oauthClientManagementList = oauthClientManagementRepository.findByUniqueKey(id, userIdList);
        if (oauthClientManagementList.size() != userIdList.size()) {
            throw new BadRequestException(ErrorCode.EWA4XX023);
        }
        for (final var oauthClientManagement : oauthClientManagementList) {
            oauthClientManagementRepository.delete(oauthClientManagement);
        }
    }
}
