package jp.co.project.planets.earthly.db.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;

import jp.co.project.planets.earthly.db.annotation.ConfigAutowireable;
import jp.co.project.planets.earthly.db.dao.base.OauthClientRedirectUrlBaseDao;

/**
 * redirect uri dao
 */
@Dao
@ConfigAutowireable
public interface RedirectUriDao extends OauthClientRedirectUrlBaseDao {
    @Select
    List<String> selectByClientId(String clientId);
}
