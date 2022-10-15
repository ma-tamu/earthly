package jp.co.project.planets.earthly.repository;

import jp.co.project.planets.earthly.db.dao.OAuthClientDao;
import org.springframework.stereotype.Repository;

/**
 * oauth client repository
 */
@Repository
public class OAuthClientRepository {

    private final OAuthClientDao oauthClientDao;

    public OAuthClientRepository(final OAuthClientDao oauthClientDao) {
        this.oauthClientDao = oauthClientDao;
    }
}
