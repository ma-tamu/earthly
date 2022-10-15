package jp.co.project.planets.earthly.db.dao;

import jp.co.project.planets.earthly.db.dao.base.OauthClientConsentBaseDao;
import org.seasar.doma.Dao;
import org.seasar.doma.boot.ConfigAutowireable;

/**
 * oauth client consent dao
 */
@Dao
@ConfigAutowireable
public interface OAuthClientConsentDao extends OauthClientConsentBaseDao {
}
