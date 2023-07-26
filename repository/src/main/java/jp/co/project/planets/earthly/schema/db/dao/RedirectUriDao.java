package jp.co.project.planets.earthly.schema.db.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.project.planets.earthly.schema.db.dao.base.OauthClientRedirectUrlBaseDao;

/**
 * redirect uri dao
 */
@Dao
@ConfigAutowireable
public interface RedirectUriDao extends OauthClientRedirectUrlBaseDao {
    @Select
    List<String> selectByClientId(String clientId);
}
