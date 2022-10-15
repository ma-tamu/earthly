package jp.co.project.planets.earthly.db.dao;

import jp.co.project.planets.earthly.db.dao.base.OauthClientConsentBaseDao;
import jp.co.project.planets.earthly.db.entity.OauthClientConsent;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.Optional;

/**
 * oauth client consent dao
 */
@Dao
@ConfigAutowireable
public interface OAuthClientConsentDao extends OauthClientConsentBaseDao {

    /**
     * ユーザー名からOAuthクライアント認証を取得する
     *
     * @param registeredClientId
     *         クライアントID
     * @param principalName
     *         ユーザー名(ログインID)
     * @return OauthClientConsent
     */
    @Select
    Optional<OauthClientConsent> selectConsentClientIdByUserId(String registeredClientId, String principalName);
}
