package jp.co.project.planets.earthly.schema.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.seasar.doma.boot.Pageables;
import org.seasar.doma.jdbc.criteria.Entityql;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.GrantTypeDao;
import jp.co.project.planets.earthly.schema.db.dao.LogoutRedirectUrlDao;
import jp.co.project.planets.earthly.schema.db.dao.OAuthClientDao;
import jp.co.project.planets.earthly.schema.db.dao.OAuthClientManagementDao;
import jp.co.project.planets.earthly.schema.db.dao.RedirectUriDao;
import jp.co.project.planets.earthly.schema.db.dao.ScopeDao;
import jp.co.project.planets.earthly.schema.db.entity.LogoutRedirectUrl_;
import jp.co.project.planets.earthly.schema.db.entity.OauthClient;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientRedirectUrl_;
import jp.co.project.planets.earthly.schema.emuns.PermissionEnum;
import jp.co.project.planets.earthly.schema.model.dto.OAuthClientSearchResultDto;
import jp.co.project.planets.earthly.schema.model.entity.OAuthClientDetailEntity;
import jp.co.project.planets.earthly.schema.model.entity.OAuthClientEntity;

/**
 * oauth client repository
 */
@Repository
public class OAuthClientRepository {

    private final OAuthClientDao oauthClientDao;
    private final GrantTypeDao grantTypeDao;
    private final ScopeDao scopeDao;
    private final RedirectUriDao redirectUriDao;
    private final LogoutRedirectUrlDao logoutRedirectUrlDao;
    private final OAuthClientManagementDao oauthClientManagementDao;

    private final Entityql entityql;

    public OAuthClientRepository(final OAuthClientDao oauthClientDao, final GrantTypeDao grantTypeDao,
            final ScopeDao scopeDao, final RedirectUriDao redirectUriDao,
            final LogoutRedirectUrlDao logoutRedirectUrlDao, final OAuthClientManagementDao oauthClientManagementDao,
            final Entityql entityql) {
        this.oauthClientDao = oauthClientDao;
        this.grantTypeDao = grantTypeDao;
        this.scopeDao = scopeDao;
        this.redirectUriDao = redirectUriDao;
        this.logoutRedirectUrlDao = logoutRedirectUrlDao;
        this.oauthClientManagementDao = oauthClientManagementDao;
        this.entityql = entityql;
    }

    /**
     * find by id
     *
     * @param id
     *            id
     * @return OAuthClientEntity
     */
    public OAuthClientEntity findById(final String id) {
        final var oauthClient = oauthClientDao.selectById(id);
        if (Objects.isNull(oauthClient)) {
            return null;
        }
        return generateOAuthClientEntity(oauthClient);
    }

    /**
     * find by client id
     *
     * @param clientId
     *            client id
     * @return OAuthClientEntity
     */
    public OAuthClientEntity findByClientId(final String clientId) {
        return oauthClientDao.selectByClientId(clientId).map(this::generateOAuthClientEntity).orElse(null);
    }

    /**
     * OAuthClientEntityを生成
     *
     * @param oauthClient
     *            OAuthクライアント
     * @return OAuthClientEntity
     */
    private OAuthClientEntity generateOAuthClientEntity(final OauthClient oauthClient) {
        final var id = oauthClient.getId();
        final var grantTypes = grantTypeDao.selectByClientId(id);
        final var scopes = scopeDao.selectByClientId(id);
        final var redirectUris = redirectUriDao.selectByClientId(id);
        final var logoutRedirectUrls = logoutRedirectUrlDao.selectByClientId(id);

        return new OAuthClientEntity(id, oauthClient.getClientId(), oauthClient.getClientSecret(),
                oauthClient.getName(), scopes, grantTypes, redirectUris, logoutRedirectUrls);
    }

    /**
     * 閲覧できるOAuthクライアントを取得
     *
     * @param id
     *            OAuthクライアントID
     * @param permissionEnumList
     *            パーミッションリスト
     * @param operatorUserId
     *            操作ユーザーID
     * @return OAuthClientEntity
     */
    public Optional<OAuthClientDetailEntity> findAccessibleById(final String id,
            final List<PermissionEnum> permissionEnumList, final String operatorUserId) {
        final var hasViewAllOAuthClient = permissionEnumList.contains(PermissionEnum.VIEW_ALL_OAUTH_CLIENT);
        final var hasViewAllUser = permissionEnumList.contains(PermissionEnum.VIEW_ALL_USER);
        return oauthClientDao.selectAccessibleById(id, hasViewAllOAuthClient, operatorUserId)
                .map(entity -> generateOAuthClientDetailEntity(entity, hasViewAllUser, operatorUserId));
    }

    /**
     * OAuthクライアント詳細エンティティを生成
     * 
     * @param oauthClient
     *            OAuthクライアント
     * @param hasViewAllUser
     *            view_all_userを付与さているか
     * @param operatorUserId
     *            操作ユーザーID
     * @return OAuthClientDetailEntity
     */
    private OAuthClientDetailEntity generateOAuthClientDetailEntity(final OauthClient oauthClient,
            final boolean hasViewAllUser, final String operatorUserId) {
        final var id = oauthClient.getId();
        final var grantTypes = grantTypeDao.selectByClientId(id);
        final var scopes = scopeDao.selectByClientId(id);
        final var oauthClientRedirectUrl = new OauthClientRedirectUrl_();
        final var redirectUris = entityql.from(oauthClientRedirectUrl)
                .where(w -> w.eq(oauthClientRedirectUrl.oauthClientId, id))
                .orderBy(o -> o.asc(oauthClientRedirectUrl.id)).fetch();
        final var logoutRedirectUrl = new LogoutRedirectUrl_();
        final var logoutRedirectUrls = entityql.from(logoutRedirectUrl)
                .where(w -> w.eq(logoutRedirectUrl.oauthClientId, id)).orderBy(o -> o.asc(logoutRedirectUrl.createdAt))
                .fetch();
        final var managementUsers = oauthClientManagementDao.selectAccessibleByClientId(id, hasViewAllUser,
                operatorUserId);
        return new OAuthClientDetailEntity(id, oauthClient.getClientId(), oauthClient.getClientSecret(),
                oauthClient.getName(), scopes, grantTypes, redirectUris, logoutRedirectUrls, managementUsers);
    }

    /**
     * 閲覧できるOAuthクライアントリスト取得
     *
     * @param permissionEnumList
     *            パーミッションリスト
     * @param operatorUserId
     *            操作ユーザーID
     * @return OAuthクライアントリスト
     */
    public List<OauthClient> findByAccessible(final List<PermissionEnum> permissionEnumList,
            final String operatorUserId) {
        final var hasViewAllOAuthClient = permissionEnumList.contains(PermissionEnum.VIEW_ALL_OAUTH_CLIENT);
        return oauthClientDao.selectByAccessible(hasViewAllOAuthClient, operatorUserId);
    }

    /**
     * OAuthクライアント名からOAuthクライアントリストを取得
     *
     * @param name
     *            OAuthクライアント名
     * @param pageable
     *            ページャー
     * @param permissionEnumList
     *            パーミッションリスト
     * @param operatorUserId
     *            操作ユーザーID
     * @return OAuthクライアントリスト
     */
    public OAuthClientSearchResultDto findByName(final String name, final Pageable pageable,
            final List<PermissionEnum> permissionEnumList, final String operatorUserId) {
        final var selectOptions = Pageables.toSelectOptions(pageable).count();
        final var hasViewAllOAuthClient = permissionEnumList.contains(PermissionEnum.VIEW_ALL_OAUTH_CLIENT);
        final var oauthClientList = oauthClientDao.selectByName(name, hasViewAllOAuthClient, operatorUserId,
                selectOptions);
        return new OAuthClientSearchResultDto(oauthClientList, pageable.getOffset(), selectOptions.getCount());
    }

    public Optional<OauthClient> findAccessibleByName(final String name, final List<PermissionEnum> permissionEnumList,
            final String operationUserId) {
        final var hasViewAllOAuthClient = permissionEnumList.contains(PermissionEnum.VIEW_ALL_OAUTH_CLIENT);
        return oauthClientDao.selectByAccessibleName(name, hasViewAllOAuthClient, operationUserId);
    }

    /**
     * insert oauthClient
     *
     * @param oauthClient
     *            oauthClient
     * @return insert count
     */
    public int insert(final OauthClient oauthClient) {
        final var localDateTime = LocalDateTime.now();
        oauthClient.setCreatedAt(localDateTime);
        oauthClient.setUpdatedAt(localDateTime);
        return oauthClientDao.insert(oauthClient);
    }
}
