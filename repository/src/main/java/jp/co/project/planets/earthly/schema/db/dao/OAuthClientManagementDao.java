package jp.co.project.planets.earthly.schema.db.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.project.planets.earthly.schema.db.dao.base.OauthClientManagementBaseDao;
import jp.co.project.planets.earthly.schema.db.entity.User;

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
}
