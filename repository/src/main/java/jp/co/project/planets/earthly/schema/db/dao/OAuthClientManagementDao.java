package jp.co.project.planets.earthly.schema.db.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import jp.co.project.planets.earthly.schema.db.dao.base.OauthClientManagementBaseDao;
import jp.co.project.planets.earthly.schema.db.entity.User;
import jp.co.project.planets.earthly.schema.model.entity.OAuthClientManagementUserEntity;

/**
 * oauth client management dao
 */
@Dao
@ConfigAutowireable
public interface OAuthClientManagementDao extends OauthClientManagementBaseDao {

    /**
     * 閲覧できるOAuthクライアント管理者リストを取得
     * 
     * @param clientId
     *            OAuthクライアントID
     * @param hasViewAllUser
     *            view_all_userを保持しているか
     * @param operatorUserId
     *            操作ユーザーID
     * @return user list
     */
    @Select
    List<User> selectAccessibleByClientId(String clientId, boolean hasViewAllUser, String operatorUserId);

    /**
     * 閲覧できるOAuthクライアント管理者リストを取得
     *
     * @param clientId
     *            OAuthクライアントID
     * @param hasViewAllUser
     *            view_all_userを保持しているか
     * @param operatorUserId
     *            操作ユーザーID
     * @return oauth client management user list
     */
    @Select
    List<OAuthClientManagementUserEntity> selectAccessiblyManagementUserByClientId(String clientId,
        boolean hasViewAllUser, String operatorUserId);

    /**
     * 閲覧できるOAuthクライアント管理者をリストを取得
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
     * @param options
     *            select option
     * @return user list
     */
    @Select
    List<OAuthClientManagementUserEntity> selectByAccessibleClientIdAndUserName(String clientId, String userName,
        boolean hasViewAllClient, boolean hasViewAllUser, String operatorUserId, SelectOptions options);
}
