package jp.co.project.planets.earthly.auth.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jp.co.project.planets.earthly.auth.helper.ConvertHelper;
import jp.co.project.planets.earthly.auth.security.oauth2.Jwks;
import jp.co.project.planets.earthly.auth.security.oauth2.client.MoonRegisteredClientRepository;
import jp.co.project.planets.earthly.auth.security.oauth2.server.CustomOAuth2AuthorizationService;
import jp.co.project.planets.earthly.repository.OAuth2AuthorizationRepository;
import jp.co.project.planets.earthly.repository.OAuthClientConsentRepository;
import jp.co.project.planets.earthly.repository.OAuthClientRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;

import java.time.Duration;

/**
 * authorization server config
 */
@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfig {

    /**
     * generate registered client repository
     *
     * @param oauthClientRepository
     *         oauth client repository
     * @param passwordEncoder
     *         password encoder
     * @param tokenSettings
     *         token settings
     * @return RegisteredClientRepository
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository(final OAuthClientRepository oauthClientRepository,
            final PasswordEncoder passwordEncoder,
            final TokenSettings tokenSettings) {
        return new MoonRegisteredClientRepository(oauthClientRepository, passwordEncoder, tokenSettings);
    }

    /**
     * generate jwk
     *
     * @return JWKSource
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        final var rsaKey = Jwks.generateRsa();
        final var jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    /**
     * generate oauth2 authorization service
     *
     * @param moonRegisteredClientRepository
     *         registered client repository
     * @param oauth2AuthorizationRepository
     *         oauth2 authorization repository
     * @param convertHelper
     *         convert helper
     * @return OAuth2AuthorizationService
     */
    @Bean
    public OAuth2AuthorizationService authorizationService(
            final RegisteredClientRepository moonRegisteredClientRepository,
            final OAuth2AuthorizationRepository oauth2AuthorizationRepository, final ConvertHelper convertHelper) {
        return new CustomOAuth2AuthorizationService(moonRegisteredClientRepository, oauth2AuthorizationRepository,
                convertHelper);
    }


    /**
     * generate oauth2 authorization consent service
     *
     * @param oauthClientConsentRepository
     *         oauth client consent repository
     * @param convertHelper
     *         convert helper
     * @return OAuth2AuthorizationConsentService
     */
    @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService(
            final OAuthClientConsentRepository oauthClientConsentRepository, final ConvertHelper convertHelper) {
        return new MoonOAuth2AuthorizationConsentService(oauthClientConsentDao, convertHelper);
    }

    /**
     * generate authorization server security filter chain
     *
     * @param http
     *         http security
     * @return SecurityFilterChain
     * @throws Exception
     *         failed generate security filter chain.
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(final HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http.formLogin(Customizer.withDefaults()).build();
    }


    /**
     * generate provider settings
     *
     * @return ProviderSettings
     */
    @Bean
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder().issuer("http://auth-server:9000").build();
    }

    @Bean
    public TokenSettings tokenSettings(@Value("${moon.oauth2.token.expire}") final String tokenExpireSecond) {
        final long expireSecond = Long.parseLong(tokenExpireSecond);
        return TokenSettings.builder() //
                            .accessTokenTimeToLive(Duration.ofSeconds(expireSecond)) //
                            .idTokenSignatureAlgorithm(SignatureAlgorithm.ES256) //
                            .refreshTokenTimeToLive(Duration.ofSeconds(expireSecond)) //
                            .build();
    }
}
