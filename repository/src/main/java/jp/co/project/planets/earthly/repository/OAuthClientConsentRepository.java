package jp.co.project.planets.earthly.repository;

import jp.co.project.planets.earthly.db.dao.OAuthClientConsentDao;
import jp.co.project.planets.earthly.db.entity.OauthClientConsent;
import org.springframework.stereotype.Repository;

/**
 * oauth client consent repository
 */
@Repository
public class OAuthClientConsentRepository {

    private final OAuthClientConsentDao oauthClientConsentDao;

    public OAuthClientConsentRepository(final OAuthClientConsentDao oauthClientConsentDao) {
        this.oauthClientConsentDao = oauthClientConsentDao;
    }

    public int insert(final OauthClientConsent oauthClientConsent) {
        return oauthClientConsentDao.insert(oauthClientConsent);
    }
}
