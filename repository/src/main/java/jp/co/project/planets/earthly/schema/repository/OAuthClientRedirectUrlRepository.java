package jp.co.project.planets.earthly.schema.repository;

import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.RedirectUriDao;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientRedirectUrl;

@Repository
public class OAuthClientRedirectUrlRepository {

    private final RedirectUriDao redirectUriDao;

    public OAuthClientRedirectUrlRepository(final RedirectUriDao redirectUriDao) {
        this.redirectUriDao = redirectUriDao;
    }

    public OauthClientRedirectUrl findByClientRedirectUrl(final String clientId, final String redirectUrl) {

    }
}
