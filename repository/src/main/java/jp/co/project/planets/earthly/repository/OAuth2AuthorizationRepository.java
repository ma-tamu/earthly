package jp.co.project.planets.earthly.repository;

import jp.co.project.planets.earthly.db.dao.OAuth2AuthorizationDao;
import org.springframework.stereotype.Repository;

@Repository
public class OAuth2AuthorizationRepository {

    private final OAuth2AuthorizationDao oauth2AuthorizationDao;

    public OAuth2AuthorizationRepository(final OAuth2AuthorizationDao oauth2AuthorizationDao) {
        this.oauth2AuthorizationDao = oauth2AuthorizationDao;
    }
}
