package jp.co.project.planets.earthly.schema.db.dao;

import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.project.planets.earthly.schema.db.dao.base.OauthClientBaseDao;
import jp.co.project.planets.earthly.schema.db.entity.OauthClient;

/**
 * oauth client dao
 */
@Dao
@ConfigAutowireable
public interface OAuthClientDao extends OauthClientBaseDao {

    @Select
    Optional<OauthClient> selectByClientId(String clientId);
}
