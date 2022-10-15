package jp.co.project.planets.earthly.db.dao;

import jp.co.project.planets.earthly.db.dao.base.OauthClientRedirectUrlBaseDao;
import jp.co.project.planets.earthly.db.entity.OauthClientRedirectUrl;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * redirect uri dao
 */
@Dao
@ConfigAutowireable
public interface RedirectUriDao extends OauthClientRedirectUrlBaseDao {
   @Select
    List<String> selectByClientId(String clientId);
}
