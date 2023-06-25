package jp.co.project.planets.earthly.schema.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.OAuthClientConsentDao;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientConsent;

/**
 * oauth client consent repository
 */
@Repository
public class OAuthClientConsentRepository {

    private final OAuthClientConsentDao oauthClientConsentDao;

    /**
     * new instance oauth client consent repository
     *
     * @param oauthClientConsentDao
     *            oauth client consent dao
     */
    public OAuthClientConsentRepository(final OAuthClientConsentDao oauthClientConsentDao) {
        this.oauthClientConsentDao = oauthClientConsentDao;
    }

    /**
     * ユーザー名からOAuthクライアント認証を取得する
     *
     * @param registeredClientId
     *            クライアントID
     * @param principalName
     *            ユーザー名(ログインID)
     * @return OauthClientConsent
     */
    public Optional<OauthClientConsent> selectConsentClientIdByUserId(final String registeredClientId,
            final String principalName) {
        return oauthClientConsentDao.selectConsentClientIdByUserId(registeredClientId, principalName);
    }

    /**
     * OAuthクライアント認証を登録
     *
     * @param oauthClientConsent
     *            OAuthクライアント認証
     * @return 登録件数
     */
    public int insert(final OauthClientConsent oauthClientConsent) {
        return oauthClientConsentDao.insert(oauthClientConsent);
    }

    /**
     * OAuthクライアント認証を削除
     *
     * @param oauthClientConsent
     *            OAuthクライアント認証
     * @return 削除件数
     */
    public int delete(final OauthClientConsent oauthClientConsent) {
        return oauthClientConsentDao.delete(oauthClientConsent);
    }
}
