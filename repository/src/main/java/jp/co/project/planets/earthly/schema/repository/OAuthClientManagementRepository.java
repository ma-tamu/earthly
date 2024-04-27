package jp.co.project.planets.earthly.schema.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.seasar.doma.boot.Pageables;
import org.seasar.doma.jdbc.criteria.Entityql;
import org.seasar.doma.jdbc.criteria.NativeSql;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.OAuthClientManagementDao;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientManagement;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientManagement_;
import jp.co.project.planets.earthly.schema.model.entity.OAuthClientManagementUserEntity;

/**
 * oauth client management repository
 */
@Repository
public class OAuthClientManagementRepository {

    private final OAuthClientManagementDao oauthClientManagementDao;
    private final Entityql entityql;
    private final NativeSql nativeSql;

    public OAuthClientManagementRepository(final OAuthClientManagementDao oauthClientManagementDao,
        final Entityql entityql, final NativeSql nativeSql) {
        this.oauthClientManagementDao = oauthClientManagementDao;
        this.entityql = entityql;
        this.nativeSql = nativeSql;
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
        return entityql.from(oauthClientManagement).where(w -> {
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
     * @param hasViewAllClient
     *            view_all_clientを保持しているか
     * @param hasViewAllUser
     *            view_all_userを保持しているか
     * @param operatorUserId
     *            操作ユーザーID
     * @param pageable
     *            ページャー
     * @return OAuthクライアント管理者リスト
     */
    public List<OAuthClientManagementUserEntity> findByAccessibleClientIdAndUserId(final String clientId,
        final String userName, final boolean hasViewAllClient, final boolean hasViewAllUser,
        final String operatorUserId, final Pageable pageable) {
        final var options = Pageables.toSelectOptions(pageable);
        return oauthClientManagementDao.selectByAccessibleClientIdAndUserName(clientId, userName, hasViewAllClient,
                hasViewAllUser, operatorUserId, options);
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

    /**
     * oauthクライアントIDでOAuthクライアント管理者を削除
     * 
     * @param oauthClientId
     *            OAuthクライアントID
     * @return 削除件数
     */
    public int delete(final String oauthClientId) {
        final var oauthClientManagement = new OauthClientManagement_();
        return nativeSql.delete(oauthClientManagement)
                .where(w -> w.eq(oauthClientManagement.oauthClientId, oauthClientId)).execute();
    }
}
