package jp.co.project.planets.earthly.schema.db.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;

import jp.co.project.planets.earthly.schema.db.annotation.ConfigAutowireable;
import jp.co.project.planets.earthly.schema.db.dao.base.LogoutRedirectUrlBaseDao;

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
}
