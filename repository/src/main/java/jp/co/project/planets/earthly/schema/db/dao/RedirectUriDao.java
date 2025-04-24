package jp.co.project.planets.earthly.schema.db.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import jp.co.project.planets.earthly.schema.db.dao.base.OauthClientRedirectUrlBaseDao;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientRedirectUrl;

/**
 * redirect uri dao
 */
@Dao
@ConfigAutowireable
public interface RedirectUriDao extends OauthClientRedirectUrlBaseDao {
    @Select
    List<String> selectByClientId(String clientId);

    /**
     * 入力されたリダイレクトURIとOAuthクライアントIDで対象のリダイレクトURIを取得する。
     * 
     * @param clientId
     *            OAuthクライアントID
     * @param redirectUrl
     *            リダイレクトURI
     * @param hasViewAllOAuthClient
     *            すべてのOAuthクライアント閲覧有無
     * @param operatorUserId
     *            操作ユーザーID
     * @param options
     *            select option
     * @return リダイレクトURIリスト
     */
    @Select
    List<OauthClientRedirectUrl> selectByClientRedirectUrl(String clientId, String redirectUrl,
        boolean hasViewAllOAuthClient, String operatorUserId, SelectOptions options);

    @Select
    List<OauthClientRedirectUrl> selectByClientIdAndRedirectUris(String clientId, List<String> redirectUriIdList,
        boolean hasViewAllOAuthClient, String operatorUserId, SelectOptions option);
}
