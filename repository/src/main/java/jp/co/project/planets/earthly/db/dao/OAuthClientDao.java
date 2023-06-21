package jp.co.project.planets.earthly.db.dao;

import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;

import jp.co.project.planets.earthly.db.annotation.ConfigAutowireable;
import jp.co.project.planets.earthly.db.dao.base.OauthClientBaseDao;
import jp.co.project.planets.earthly.db.entity.OauthClient;

/**
 * oauth client dao
 */
@Dao
@ConfigAutowireable
public interface OAuthClientDao extends OauthClientBaseDao {

    @Select
    Optional<OauthClient> selectByClientId(String clientId);
}
