package jp.co.project.planets.earthly.db.dao;

import jp.co.project.planets.earthly.db.dao.base.GrantTypeBaseDao;
import jp.co.project.planets.earthly.db.entity.GrantType;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * grant type dop
 */
@Dao
@ConfigAutowireable
public interface GrantTypeDao extends GrantTypeBaseDao {

    /**
     * OAuthクライアントIDから認可タイプリストを取得
     *
     * @param clientId
     *         OAuthクライアントID
     * @return 認可タイプリスト
     */
    @Select
    List<GrantType> selectByClientId(String clientId);
}
