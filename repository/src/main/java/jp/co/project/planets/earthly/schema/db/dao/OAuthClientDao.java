package jp.co.project.planets.earthly.schema.db.dao;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import jp.co.project.planets.earthly.schema.db.dao.base.OauthClientBaseDao;
import jp.co.project.planets.earthly.schema.db.entity.OauthClient;

/**
 * oauth client dao
 */
@Dao
@ConfigAutowireable
public interface OAuthClientDao extends OauthClientBaseDao {

    @Select
    Optional<OauthClient> selectByClientId(String clientId);

    /**
     * 閲覧できるOAuthクライアント取得
     *
     * @param hasViewAllOAuthClient
     *            view_all_oauth_clientを持っているか
     * @param operatorUserId
     *            操作ユーザーID
     * @return OAuthクライアントリスト
     */
    @Select
    List<OauthClient> selectByAccessible(boolean hasViewAllOAuthClient, String operatorUserId);

    /**
     * 閲覧できるOAuthクライアント取得
     * 
     * @param id
     *            OAuthクライアントID
     * @param hasViewAllOAuthClient
     *            view_all_oauth_clientを持っているか
     * @param operatorUserId
     *            操作ユーザーID
     * @return OAuthクライアント
     */
    @Select
    Optional<OauthClient> selectAccessibleById(String id, boolean hasViewAllOAuthClient, String operatorUserId);

    /**
     * OAuthクライアント名で検索
     *
     * @param name
     *            OAuthクライアント名
     * @param hasViewAllOAuthClient
     *            view_all_oauth_clientを持っているか
     * @param operatorUserId
     *            操作ユーザーID
     * @param options
     *            selectオプション
     * @return OAuthクライアントリスト
     */
    @Select
    List<OauthClient> selectByName(String name, boolean hasViewAllOAuthClient,
            String operatorUserId, SelectOptions options);

    /**
     * クライアント名から閲覧できるOAuthクライアントを取得
     *
     * @param name
     *            OAuthクライアント名
     * @param hasViewAllOAuthClient
     *            view_all_oauth_clientを持っているか
     * @param operatorUserId
     *            操作ユーザーID
     * @return OAuthクライアント
     */
    @Select
    Optional<OauthClient> selectByAccessibleName(String name, boolean hasViewAllOAuthClient, String operatorUserId);

}
