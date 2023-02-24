package jp.co.project.planets.earthly.db.dao;

import jp.co.project.planets.earthly.db.dao.base.OauthClientBaseDao;
import jp.co.project.planets.earthly.db.entity.OauthClient;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.Optional;

/**
 * oauth client dao
 */
@Dao
@ConfigAutowireable
public interface OAuthClientDao extends OauthClientBaseDao {

    @Select
    Optional<OauthClient> selectByClientId(String clientId);
}
