package jp.co.project.planets.earthly.schema.repository;

import java.time.LocalDateTime;

import org.seasar.doma.jdbc.criteria.NativeSql;
import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.OAuthClientManagementDao;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientManagement;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientManagement_;

/**
 * oauth client management repository
 */
@Repository
public class OAuthClientManagementRepository {

    private final OAuthClientManagementDao oauthClientManagementDao;
    private final NativeSql nativeSql;

    public OAuthClientManagementRepository(final OAuthClientManagementDao oauthClientManagementDao,
            final NativeSql nativeSql) {
        this.oauthClientManagementDao = oauthClientManagementDao;
        this.nativeSql = nativeSql;
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
