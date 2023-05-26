package jp.co.project.planets.earthly.auth.security.oauth2.server;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.transaction.annotation.Transactional;

import jp.co.project.planets.earthly.auth.helper.ConvertHelper;
import jp.co.project.planets.earthly.db.entity.OauthClientConsent;
import jp.co.project.planets.earthly.repository.OAuthClientConsentRepository;

/**
 * custom oauth2 authorization consent service
 */
public class CustomOAuth2AuthorizationConsentService implements OAuth2AuthorizationConsentService {

    private final OAuthClientConsentRepository oauthClientConsentRepository;
    private final ConvertHelper convertHelper;

    /**
     * new instance custom oauth2 authorization consent service
     *
     * @param oauthClientConsentRepository
     *            OAuthクライアント承認DAO
     * @param convertHelper
     *            convert helper
     */
    public CustomOAuth2AuthorizationConsentService(final OAuthClientConsentRepository oauthClientConsentRepository,
            final ConvertHelper convertHelper) {
        this.oauthClientConsentRepository = oauthClientConsentRepository;
        this.convertHelper = convertHelper;
    }

    @Override
    @Transactional
    public void save(final OAuth2AuthorizationConsent authorizationConsent) {
        final var oauthClientConsent = new OauthClientConsent();
        oauthClientConsent.setRegisteredClientId(authorizationConsent.getRegisteredClientId());
        oauthClientConsent.setPrincipalName(authorizationConsent.getPrincipalName());
        final var json = convertHelper.convertObjectIntoJson(authorizationConsent.getAuthorities());
        oauthClientConsent.setAuthorities(json);
        oauthClientConsentRepository.insert(oauthClientConsent);
    }

    @Override
    @Transactional
    public void remove(final OAuth2AuthorizationConsent authorizationConsent) {
        final var oauthClientConsentOptional = oauthClientConsentRepository.selectConsentClientIdByUserId(
                authorizationConsent.getRegisteredClientId(), authorizationConsent.getPrincipalName());
        if (oauthClientConsentOptional.isEmpty()) {
            return;
        }
        final var oauthClientConsent = oauthClientConsentOptional.get();
        oauthClientConsentRepository.delete(oauthClientConsent);
    }

    @Override
    public OAuth2AuthorizationConsent findById(final String registeredClientId, final String principalName) {
        final var oauthClientConsentOptional = oauthClientConsentRepository.selectConsentClientIdByUserId(
                registeredClientId, principalName);
        if (oauthClientConsentOptional.isEmpty()) {
            return null;
        }
        final var oauthClientConsent = oauthClientConsentOptional.get();
        final var builder = OAuth2AuthorizationConsent.withId(registeredClientId, principalName);
        final var grantedAuthoritySet = (Set<GrantedAuthority>) convertHelper.convertJsonIntoObject(
                oauthClientConsent.getAuthorities(), Set.class);
        grantedAuthoritySet.forEach(builder::authority);
        return builder.build();
    }
}
