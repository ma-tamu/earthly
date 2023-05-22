package jp.co.project.planets.earthly.auth.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import jp.co.project.planets.earthly.auth.helper.ConvertHelper;
import jp.co.project.planets.earthly.auth.security.AuthorizationProperties;
import jp.co.project.planets.earthly.auth.security.CustomAuthenticationProvider;
import jp.co.project.planets.earthly.auth.security.LoginUserDetailService;
import jp.co.project.planets.earthly.auth.security.oauth2.client.CustomRegisteredClientRepository;
import jp.co.project.planets.earthly.auth.security.oauth2.server.CustomOAuth2AuthorizationConsentService;
import jp.co.project.planets.earthly.auth.security.oauth2.server.CustomOAuth2AuthorizationService;
import jp.co.project.planets.earthly.repository.OAuth2AuthorizationRepository;
import jp.co.project.planets.earthly.repository.OAuthClientConsentRepository;
import jp.co.project.planets.earthly.repository.OAuthClientRepository;

/**
 * security config
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthorizationProperties authorizationProperties;
    private final LoginUserDetailService loginUserDetailService;
    private final OAuthClientRepository oauthClientRepository;
    private final OAuth2AuthorizationRepository authorizationRepository;
    private final OAuthClientConsentRepository authClientConsentRepository;
    private final ConvertHelper convertHelper;

    /**
     * new instances security config
     *
     * @param authorizationProperties
     * @param loginUserDetailService
     *            user detail service
     * @param oauthClientRepository
     * @param authorizationRepository
     * @param authClientConsentRepository
     * @param convertHelper
     */
    public SecurityConfig(final AuthorizationProperties authorizationProperties,
            final LoginUserDetailService loginUserDetailService, final OAuthClientRepository oauthClientRepository,
            final OAuth2AuthorizationRepository authorizationRepository,
            final OAuthClientConsentRepository authClientConsentRepository, final ConvertHelper convertHelper) {
        this.authorizationProperties = authorizationProperties;
        this.loginUserDetailService = loginUserDetailService;

        this.oauthClientRepository = oauthClientRepository;
        this.authorizationRepository = authorizationRepository;
        this.authClientConsentRepository = authClientConsentRepository;
        this.convertHelper = convertHelper;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(final HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults());
        http.exceptionHandling(
                exception -> exception.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")))
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        return http.build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        return new CustomRegisteredClientRepository(oauthClientRepository, passwordEncoder());
    }

    @Bean
    public OAuth2AuthorizationService authorizationService() {
        return new CustomOAuth2AuthorizationService(registeredClientRepository(),
                authorizationRepository, convertHelper);
    }

    @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService() {
        return new CustomOAuth2AuthorizationConsentService(authClientConsentRepository,
                convertHelper);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().issuer(authorizationProperties.getIssuer()).build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(final HttpSecurity http) throws Exception {
        final CustomAuthenticationProvider authenticationProvider = new CustomAuthenticationProvider();
        authenticationProvider.setUserDetailsService(loginUserDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return http
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers("/login", "/css/**", "/js/**", "/img/**", "/favicon.ico")
                                .permitAll()
                                .anyRequest().authenticated())
                .formLogin(config -> config.loginPage("/login"))
                .authenticationProvider(authenticationProvider).build();
    }

    /**
     * generate password encoder
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        final KeyPair keyPair = generateRsaKey();
        final RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        final RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        final RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        final JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    private static KeyPair generateRsaKey() {
        final KeyPair keyPair;
        try {
            final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (final Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    @Bean
    public JwtDecoder jwtDecoder(final JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }
}
