package jp.co.project.planets.earthly.auth.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import jp.co.project.planets.earthly.auth.helper.ConvertHelper;
import jp.co.project.planets.earthly.auth.security.oauth2.Jwks;
import jp.co.project.planets.earthly.auth.security.oauth2.client.CustomRegisteredClientRepository;
import jp.co.project.planets.earthly.auth.security.oauth2.server.CustomOAuth2AuthorizationConsentService;
import jp.co.project.planets.earthly.auth.security.oauth2.server.CustomOAuth2AuthorizationService;
import jp.co.project.planets.earthly.repository.OAuth2AuthorizationRepository;
import jp.co.project.planets.earthly.repository.OAuthClientConsentRepository;
import jp.co.project.planets.earthly.repository.OAuthClientRepository;

/**
 * authorization server config
 */
@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfig {

    /**
     * generate authorization server security filter chain
     *
     * @param http
     *            http security
     * @return SecurityFilterChain
     * @throws Exception
     *             failed generate security filter chain.
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(final HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        // OIDCを有効化
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults());

        http.exceptionHandling(
                handling -> handling.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")))
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

        return http.build();
    }

    /**
     * generate registered client repository
     *
     * @param oauthClientRepository
     *            oauth client repository
     * @param passwordEncoder
     *            password encoder
     * @param tokenSettings
     *            token settings
     * @return RegisteredClientRepository
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository(final OAuthClientRepository oauthClientRepository,
            final PasswordEncoder passwordEncoder,
            final TokenSettings tokenSettings) {
        return new CustomRegisteredClientRepository(oauthClientRepository, passwordEncoder, tokenSettings);
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

    @Bean
    public JwtDecoder jwtDecoder(final JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    /**
     * generate oauth2 authorization service
     *
     * @param registeredClientRepository
     *            registered client repository
     * @param oauth2AuthorizationRepository
     *            oauth2 authorization repository
     * @param convertHelper
     *            convert helper
     * @return OAuth2AuthorizationService
     */
    @Bean
    public OAuth2AuthorizationService authorizationService(
            final RegisteredClientRepository registeredClientRepository,
            final OAuth2AuthorizationRepository oauth2AuthorizationRepository, final ConvertHelper convertHelper) {
        return new CustomOAuth2AuthorizationService(registeredClientRepository, oauth2AuthorizationRepository,
                convertHelper);
    }

    /**
     * generate oauth2 authorization consent service
     *
     * @param oauthClientConsentRepository
     *            oauth client consent repository
     * @param convertHelper
     *            convert helper
     * @return OAuth2AuthorizationConsentService
     */
    @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService(
            final OAuthClientConsentRepository oauthClientConsentRepository, final ConvertHelper convertHelper) {
        return new CustomOAuth2AuthorizationConsentService(oauthClientConsentRepository, convertHelper);
    }

    @Bean
    public TokenSettings tokenSettings(@Value("${auth.oauth2.token.expire}") final String tokenExpireSecond) {
        final long expireSecond = Long.parseLong(tokenExpireSecond);
        return TokenSettings.builder() //
                .accessTokenTimeToLive(Duration.ofSeconds(expireSecond)) //
                .idTokenSignatureAlgorithm(SignatureAlgorithm.ES256) //
                .refreshTokenTimeToLive(Duration.ofSeconds(expireSecond)) //
                .build();
    }
}
