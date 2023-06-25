package jp.co.project.planets.earthly.schema.db.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;

import jp.co.project.planets.earthly.schema.db.annotation.ConfigAutowireable;
import jp.co.project.planets.earthly.schema.db.dao.base.GrantTypeBaseDao;
import jp.co.project.planets.earthly.schema.db.entity.GrantType;

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
     *            OAuthクライアントID
     * @return 認可タイプリスト
     */
    @Select
    List<GrantType> selectByClientId(String clientId);
}
