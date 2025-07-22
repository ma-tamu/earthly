package jp.co.project.planets.earthly.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import jp.co.project.planets.earthly.auth.helper.ConvertHelper;
import jp.co.project.planets.earthly.auth.security.oauth2.AuthorizationProperties;
import jp.co.project.planets.earthly.auth.security.oauth2.client.CustomRegisteredClientRepository;
import jp.co.project.planets.earthly.auth.security.oauth2.server.CustomOAuth2AuthorizationConsentService;
import jp.co.project.planets.earthly.auth.security.oauth2.server.CustomOAuth2AuthorizationService;
import jp.co.project.planets.earthly.auth.utils.Jwks;
import jp.co.project.planets.earthly.schema.repository.OAuth2AuthorizationRepository;
import jp.co.project.planets.earthly.schema.repository.OAuthClientConsentRepository;

@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfig {

    private final OAuth2AuthorizationRepository authorizationRepository;
    private final OAuthClientConsentRepository authClientConsentRepository;
    private final CustomRegisteredClientRepository registeredClientRepository;
    private final ConvertHelper convertHelper;
    private final AuthorizationProperties authorizationProperties;

    public AuthorizationServerConfig(final OAuth2AuthorizationRepository authorizationRepository,
        final OAuthClientConsentRepository authClientConsentRepository,
        final CustomRegisteredClientRepository registeredClientRepository,
        final ConvertHelper convertHelper, final AuthorizationProperties authorizationProperties) {
        this.authorizationRepository = authorizationRepository;
        this.authClientConsentRepository = authClientConsentRepository;
        this.registeredClientRepository = registeredClientRepository;

        this.convertHelper = convertHelper;
        this.authorizationProperties = authorizationProperties;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(final HttpSecurity http) throws Exception {
        final var authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer();
        return http.cors(Customizer.withDefaults())
                .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .with(authorizationServerConfigurer, authorizationServer -> authorizationServer
                        .authorizationEndpoint(endpoint -> endpoint.consentPage("")).oidc(Customizer.withDefaults()))
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .exceptionHandling(exception -> exception.defaultAuthenticationEntryPointFor(
                        new LoginUrlAuthenticationEntryPoint("/login"),
                        new MediaTypeRequestMatcher(MediaType.TEXT_HTML)))
                .build();
    }

    /**
     * oauth2 authorization service
     *
     * @return OAuth2AuthorizationService
     */
    @Bean
    public OAuth2AuthorizationService authorizationService() {
        return new CustomOAuth2AuthorizationService(registeredClientRepository, authorizationRepository, convertHelper);
    }

    /**
     * oauth2 authorization consent service
     *
     * @return OAuth2AuthorizationConsentService
     */
    @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService() {
        return new CustomOAuth2AuthorizationConsentService(authClientConsentRepository,
                convertHelper);
    }

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

    /**
     * authorization server setting
     *
     * @return AuthorizationServerSettings
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().issuer(authorizationProperties.getIssuer()).build();
    }
}
