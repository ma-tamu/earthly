package jp.co.project.planets.earthly.auth.security.oauth2.client;

import jp.co.project.planets.pleiades.db.entity.Scope;
import jp.co.project.planets.pleiades.model.entity.OAuthClientEntity;
import jp.co.project.planets.pleiades.repository.OAuthClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;

import java.time.Instant;

/**
 * moon registered client repository
 */
public class MoonRegisteredClientRepository implements RegisteredClientRepository {

    private final OAuthClientRepository oauthClientRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenSettings tokenSettings;

    /**
     * new instances moon registered client repository
     *
     * @param oauthClientRepository
     *         oauth client repository
     * @param passwordEncoder
     *         password encoder
     * @param tokenSettings
     *         token settings
     */
    public MoonRegisteredClientRepository(final OAuthClientRepository oauthClientRepository,
                                          final PasswordEncoder passwordEncoder,
                                          final TokenSettings tokenSettings) {
        this.oauthClientRepository = oauthClientRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenSettings = tokenSettings;
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
     *         OAuthクライアント
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
        builder.clientSecretExpiresAt(Instant.ofEpochSecond(28800));
        oauthClient.redirectUrls().forEach(builder::redirectUri);
        builder.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST);
        builder.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
        builder.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_JWT);
        builder.tokenSettings(tokenSettings);
        return builder.build();
    }
}
