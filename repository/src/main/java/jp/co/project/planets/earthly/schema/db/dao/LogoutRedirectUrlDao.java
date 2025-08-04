package jp.co.project.planets.earthly.schema.db.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import jp.co.project.planets.earthly.schema.db.dao.base.LogoutRedirectUrlBaseDao;
import jp.co.project.planets.earthly.schema.db.entity.LogoutRedirectUrl;

/**
 * logout redirect url
 */
@Dao
@ConfigAutowireable
public interface LogoutRedirectUrlDao extends LogoutRedirectUrlBaseDao {

    /**
     * クライアントIDからログアウトリダイレクトURLを取得
     * 
     * @param clientId
     *            クライアント
     * @return ログアウトリダイレクトURL
     */
    @Select
    List<String> selectByClientId(String clientId);

    /**
     * 対象OAuthクライアントのログアウトリダイレクトURLを取得
     * 
     * @param clientId
     *            OAuthクライアントID
     * @param logoutRedirectUrl
     *            ログアウトリダイレクトURL
     * @param hasViewAllOAuthClient
     *            view_all_clientを保持しているか
     * @param operatorUserId
     *            操作ユーザーID
     * @param options
     *            SELECTオプション
     * @return OAuthクライアントリダイレクトURL
     */
    @Select
    List<LogoutRedirectUrl> selectByClientLogoutRedirectUrl(String clientId, String logoutRedirectUrl,
        boolean hasViewAllOAuthClient, String operatorUserId, SelectOptions options);

    /**
     * 対象OAuthクライアントに紐づくログアウトリダイレクトURIを取得
     * 
     * @param clientId
     *            OAuthクライアントID
     * @param redirectUriIdList
     *            ログアウトリダイレクトURL IDリスト
     * @param hasViewAllOAuthClient
     *            view_all_clientを保持しているか
     * @param operatorUserId
     *            操作ユーザー
     * @return OAuthクライアントログアウトURIリスト
     */
    @Select
    List<LogoutRedirectUrl> selectByClientIdAndRedirectUris(String clientId, List<String> redirectUriIdList,
        boolean hasViewAllOAuthClient, String operatorUserId);
}
