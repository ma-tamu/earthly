package jp.co.project.planets.earthly.auth.security.oauth2.client;

import java.time.Duration;
import java.time.Instant;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import jp.co.project.planets.earthly.schema.db.entity.Scope;
import jp.co.project.planets.earthly.schema.model.entity.OAuthClientEntity;
import jp.co.project.planets.earthly.schema.repository.OAuthClientRepository;

/**
 * custom registered client repository
 */
public class CustomRegisteredClientRepository implements RegisteredClientRepository {

    private final OAuthClientRepository oauthClientRepository;
    private final PasswordEncoder passwordEncoder;
    private static final int TOKEN_EXPIRE = 64800;

    /**
     * new instances custom registered client repository
     *
     * @param oauthClientRepository
     *            oauth client repository
     * @param passwordEncoder
     *            password encoder
     */
    public CustomRegisteredClientRepository(final OAuthClientRepository oauthClientRepository,
            final PasswordEncoder passwordEncoder) {
        this.oauthClientRepository = oauthClientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(final RegisteredClient registeredClient) {
    }

    @Override
    public RegisteredClient findById(final String id) {
        final var oauthClientEntity = oauthClientRepository.findById(id);
        return generateRegisteredClient(oauthClientEntity);
    }

    @Override
    public RegisteredClient findByClientId(final String clientId) {
        final var oauthClientEntity = oauthClientRepository.findByClientId(clientId);
        return generateRegisteredClient(oauthClientEntity);
    }

    /**
     * RegisteredClientを生成
     *
     * @param oauthClient
     *            OAuthクライアント
     * @return RegisteredClient
     */
    private RegisteredClient generateRegisteredClient(final OAuthClientEntity oauthClient) {
        final var builder = RegisteredClient.withId(oauthClient.id());
        builder.clientId(oauthClient.clientId());
        builder.clientSecret(passwordEncoder.encode(oauthClient.secret()));
        builder.clientName(oauthClient.name());
        oauthClient.scopes().stream().map(Scope::getName).forEach(builder::scope);
        oauthClient.grantTypes().stream().map(it -> new AuthorizationGrantType(it.getType())) //
                .forEach(builder::authorizationGrantType);
        builder.clientSecretExpiresAt(Instant.now().plusSeconds(28800));
        oauthClient.redirectUrls().forEach(builder::redirectUri);
        oauthClient.logoutRedirectUrls().forEach(builder::postLogoutRedirectUri);
        builder.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST);
        builder.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
        builder.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_JWT);
        final var tokenSettings = TokenSettings.builder().accessTokenTimeToLive(Duration.ofSeconds(TOKEN_EXPIRE))
                .authorizationCodeTimeToLive(Duration.ofMinutes(5)).refreshTokenTimeToLive(Duration.ofHours(24))
                .reuseRefreshTokens(false).build();
        builder.tokenSettings(tokenSettings);
        builder.clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build());
        return builder.build();
    }
}
