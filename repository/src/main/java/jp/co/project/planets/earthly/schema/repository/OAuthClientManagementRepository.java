package jp.co.project.planets.earthly.schema.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.seasar.doma.boot.Pageables;
import org.seasar.doma.jdbc.criteria.QueryDsl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.core.account.Account;
import jp.co.project.planets.earthly.schema.db.dao.OAuthClientManagementDao;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientManagement;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientManagement_;
import jp.co.project.planets.earthly.schema.emuns.PermissionEnum;
import jp.co.project.planets.earthly.schema.model.dto.OAuthClientManagementUserSearchResultDto;
import jp.co.project.planets.earthly.schema.model.entity.OAuthClientManagementUserEntity;

/**
 * oauth client management repository
 */
@Repository
public class OAuthClientManagementRepository {

    private final OAuthClientManagementDao oauthClientManagementDao;
    private final QueryDsl queryDsl;

    public OAuthClientManagementRepository(final OAuthClientManagementDao oauthClientManagementDao,
        final QueryDsl queryDsl) {
        this.oauthClientManagementDao = oauthClientManagementDao;
        this.queryDsl = queryDsl;
    }

    /**
     * OAuthクライアントIDとユーザIDでOAuthクライアント管理者を取得
     * 
     * @param oauthClientId
     *            OAuthクライアントID
     * @param userId
     *            ユーザID
     * @return OAuthクライアント管理者
     */
    public Optional<OauthClientManagement> findByOAuthClientIdAndUserId(final String oauthClientId,
        final String userId) {
        final var oauthClientManagement = new OauthClientManagement_();
        return queryDsl.from(oauthClientManagement).where(w -> {
            w.eq(oauthClientManagement.oauthClientId, oauthClientId);
            w.eq(oauthClientManagement.userId, userId);
        }).fetchOptional();
    }

    /**
     * ユーザー名でOAuthクライアント管理者を取得する。
     * 
     * @param clientId
     *            OAuthクライアントID
     * @param userName
     *            ユーザー名
     * @param companyName
     *            会社名
     * @param account
     *            操作ユーザー
     * @param pageable
     *            ページャー
     * @return OAuthクライアント管理者リスト
     */
    public List<OAuthClientManagementUserEntity> findByAccessibleClientIdAndUserId(final String clientId,
        final String userName, final String companyName, final Pageable pageable, final Account account) {
        final var options = Pageables.toSelectOptions(pageable);
        final boolean hasViewAllClient = account.permissions().contains(PermissionEnum.VIEW_ALL_OAUTH_CLIENT);
        final boolean hasViewAllUser = account.permissions().contains(PermissionEnum.VIEW_ALL_USER);
        return oauthClientManagementDao.selectByAccessibleClientIdAndUserName(clientId, userName, companyName,
                hasViewAllClient, hasViewAllUser, account.id(), options);
    }

    public OAuthClientManagementUserSearchResultDto findAccessibleUnassignedUserByAnyKeyword(final String clientId,
        final String loginId, final String userName, final String companyName, final Pageable pageable,
        final Account account) {
        final var options = Pageables.toSelectOptions(pageable);
        final boolean hasViewAllUser = account.permissions().contains(PermissionEnum.VIEW_ALL_USER);
        final var oauthClientManagementUserEntityList = oauthClientManagementDao
                .selectAccessibleUnassignedUserByAnyKeyword(clientId, loginId, userName, companyName,
                        hasViewAllUser, account.id(), options);
        return new OAuthClientManagementUserSearchResultDto(oauthClientManagementUserEntityList, pageable.getOffset(),
                options.getCount());
    }

    public List<OauthClientManagement> findAccessibleByClientIdAndInUserId(final String clientId,
        final List<String> userIdList, final Account account) {
        final boolean hasViewAllClient = account.permissions().contains(PermissionEnum.VIEW_ALL_OAUTH_CLIENT);
        final boolean hasViewAllUser = account.permissions().contains(PermissionEnum.VIEW_ALL_USER);
        return oauthClientManagementDao.selectAccessibleByClientIdAndInUserId(clientId, userIdList, hasViewAllClient,
                hasViewAllUser, account.id());
    }

    public List<OauthClientManagement> findByUniqueKey(final String clientId, final List<String> userIdList) {
        final var oauthClientManagement = new OauthClientManagement_();
        return queryDsl.from(oauthClientManagement).where(w -> {
            w.eq(oauthClientManagement.oauthClientId, clientId);
            w.in(oauthClientManagement.userId, userIdList);
        }).execute();
    }

    /**
     * OAuthクライアント管理者登録
     * 
     * @param oauthClientId
     *            OAuthクライアントID
     * @param userId
     *            ユーザーID
     * @return 登録件数
     */
    public int insert(final String oauthClientId, final String userId) {
        final var localDateTime = LocalDateTime.now();
        final var oauthClientManagement = new OauthClientManagement(null, oauthClientId, userId, localDateTime, userId);
        return oauthClientManagementDao.insert(oauthClientManagement);
    }

    public int insert(final OauthClientManagement oauthClientManagement) {
        return oauthClientManagementDao.insert(oauthClientManagement);
    }

    /**
     * oauthクライアントIDでOAuthクライアント管理者を削除
     * 
     * @param oauthClientId
     *            OAuthクライアントID
     * @return 削除件数
     */
    public int delete(final String oauthClientId) {
        final var oauthClientManagement = new OauthClientManagement_();
        return queryDsl.delete(oauthClientManagement)
                .where(w -> w.eq(oauthClientManagement.oauthClientId, oauthClientId)).execute();
    }

    public int delete(final OauthClientManagement oauthClientManagement) {
        return oauthClientManagementDao.delete(oauthClientManagement);
    }
}
