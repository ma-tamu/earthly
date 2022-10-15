package jp.co.project.planets.earthly.auth.security.oauth2.server;

import jp.co.project.planets.earthly.auth.helper.ConvertHelper;
import jp.co.project.planets.earthly.common.utils.DateUtils;
import jp.co.project.planets.earthly.db.entity.Oauth2Authorization;
import jp.co.project.planets.earthly.repository.OAuth2AuthorizationRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * moon oauth2 authorization service
 */
public class CustomOAuth2AuthorizationService implements OAuth2AuthorizationService {

    private final RegisteredClientRepository registeredClientRepository;
    private final OAuth2AuthorizationRepository oauth2AuthorizationRepository;
    private final ConvertHelper convertHelper;

    /**
     * new instances moon oauth2 authorization service.
     *
     * @param registeredClientRepository
     *         RegisteredClientRepository
     * @param oauth2AuthorizationRepository
     *         OAuth2AuthorizationRepository
     * @param convertHelper
     *         ConvertHelper
     */
    public CustomOAuth2AuthorizationService(final RegisteredClientRepository registeredClientRepository,
                                            final OAuth2AuthorizationRepository oauth2AuthorizationRepository,
                                            final ConvertHelper convertHelper) {
        this.registeredClientRepository = registeredClientRepository;
        this.oauth2AuthorizationRepository = oauth2AuthorizationRepository;
        this.convertHelper = convertHelper;
    }

    @Override
    @Transactional
    public void save(final OAuth2Authorization authorization) {
        if (StringUtils.isNotBlank(authorization.getId())) {
            final var oauth2Authorization = generateOauth2Authorization(authorization);
            oauth2AuthorizationRepository.insert(oauth2Authorization);
        } else {
            final var oauth2Authorization = oauth2AuthorizationRepository.findByPrimaryKey(authorization.getId());
            final var updatedOAuth2Authorization = generateOauth2Authorization(authorization, oauth2Authorization);
            oauth2AuthorizationRepository.update(updatedOAuth2Authorization);
        }
    }

    /**
     * Oauth2Authorizationを生成
     *
     * @param authorization
     *         OAuth2Authorization
     *
     * @return Oauth2Authorization
     */
    private Oauth2Authorization generateOauth2Authorization(final OAuth2Authorization authorization) {
        return generateOauth2Authorization(authorization, new Oauth2Authorization());
    }

    /**
     * Oauth2Authorizationを生成
     *
     * @param authorization
     *         OAuth2Authorization
     * @param oauth2Authorization
     *         Oauth2Authorization
     *
     * @return Oauth2Authorization
     */
    private Oauth2Authorization generateOauth2Authorization(final OAuth2Authorization authorization,
                                                            final Oauth2Authorization oauth2Authorization) {
        oauth2Authorization.setId(authorization.getId());
        oauth2Authorization.setRegisteredClientId(authorization.getRegisteredClientId());
        oauth2Authorization.setPrincipalName(authorization.getPrincipalName());
        oauth2Authorization.setAuthorizationGrantType(authorization.getAuthorizationGrantType().getValue());
        final var authorizationCode = authorization.getToken(OAuth2AuthorizationCode.class);
        if (Objects.nonNull(authorizationCode)) {
            final var authorizationCodeToken = authorizationCode.getToken();
            oauth2Authorization.setAuthorizationGrantType(oauth2Authorization.getAuthorizationGrantType());
            oauth2Authorization.setAuthorizationCodeValue((authorizationCodeToken.getTokenValue()));
            oauth2Authorization.setAuthorizationCodeExpiresAt(
                    DateUtils.toLocalDateTime(authorizationCodeToken.getExpiresAt()));
            oauth2Authorization.setAuthorizationCodeIssuedAt(
                    DateUtils.toLocalDateTime(authorizationCodeToken.getIssuedAt()));
            final var metadataJson = convertHelper.convertObjectIntoJson(authorizationCode.getMetadata());
            oauth2Authorization.setAuthorizationCodeMetadata(metadataJson);
        }
        final var attribute = convertHelper.convertObjectIntoJson(authorization.getAttributes());
        oauth2Authorization.setAttributes(attribute);
        Optional.ofNullable((String) authorization.getAttribute(OAuth2ParameterNames.STATE)).ifPresent(
                oauth2Authorization::setState);

        final var oauth2AccessTokenToken = authorization.getToken(OAuth2AccessToken.class);
        if (Objects.nonNull(oauth2AccessTokenToken)) {
            final var accessToken = oauth2AccessTokenToken.getToken();
            oauth2Authorization.setAccessTokenValue(accessToken.getTokenValue());
            oauth2Authorization.setAccessTokenExpiresAt(DateUtils.toLocalDateTime(accessToken.getExpiresAt()));
            oauth2Authorization.setAccessTokenIssuedAt(DateUtils.toLocalDateTime(accessToken.getIssuedAt()));
            final var metadataJson = convertHelper.convertObjectIntoJson(oauth2AccessTokenToken.getMetadata());
            oauth2Authorization.setAccessTokenMetadata(metadataJson);
        }

        final var oidcIdTokenToken = authorization.getToken(OidcIdToken.class);
        if (Objects.nonNull(oidcIdTokenToken)) {
            final var token = oidcIdTokenToken.getToken();
            oauth2Authorization.setOidcIdTokenValue(token.getTokenValue());
            oauth2Authorization.setOidcIdTokenExpiresAt(DateUtils.toLocalDateTime(token.getExpiresAt()));
            oauth2Authorization.setOidcIdTokenIssuedAt(DateUtils.toLocalDateTime(token.getIssuedAt()));
            final var metadataJson = convertHelper.convertObjectIntoJson(oidcIdTokenToken.getMetadata());
            oauth2Authorization.setOidcIdTokenMetadata(metadataJson);
        }
        final var refreshTokenToken = authorization.getRefreshToken();
        if (Objects.nonNull(refreshTokenToken)) {
            final var token = refreshTokenToken.getToken();
            oauth2Authorization.setRefreshTokenValue(token.getTokenValue());
            oauth2Authorization.setRefreshTokenExpiresAt(DateUtils.toLocalDateTime(token.getExpiresAt()));
            oauth2Authorization.setRefreshTokenIssuedAt(DateUtils.toLocalDateTime(token.getIssuedAt()));
            final var metadataJson = convertHelper.convertObjectIntoJson(refreshTokenToken.getMetadata());
            oauth2Authorization.setRefreshTokenMetadata(metadataJson);
        }
        return oauth2Authorization;
    }

    @Override
    public void remove(final OAuth2Authorization authorization) {
        final var oauth2Authorization = oauth2AuthorizationRepository.findByPrimaryKey(authorization.getId());
        oauth2AuthorizationRepository.delete(oauth2Authorization);
    }

    @Override
    public OAuth2Authorization findById(final String id) {
        final var oauth2Authorization = oauth2AuthorizationRepository.findByPrimaryKey(id);
        return generateOAuth2Authorization(oauth2Authorization);
    }

    @Override
    public OAuth2Authorization findByToken(final String token, final OAuth2TokenType tokenType) {

        if (Objects.isNull(tokenType)) {
            final var oauth2Authorization = oauth2AuthorizationRepository.findByUnknownToken(token);
            return generateOAuth2Authorization(oauth2Authorization);
        }

        final var oauth2Authorization = switch (tokenType.getValue()) {
            case OAuth2ParameterNames.STATE -> oauth2AuthorizationRepository.findByState(token);
            case OAuth2ParameterNames.CODE -> oauth2AuthorizationRepository.findByCode(token);
            case OAuth2ParameterNames.ACCESS_TOKEN -> oauth2AuthorizationRepository.findByAccessToken(token);
            case OAuth2ParameterNames.REFRESH_TOKEN -> oauth2AuthorizationRepository.findByRefreshToken(token);
            default -> throw new RuntimeException(String.format("unknown token. token=%s", token));
        };
        return generateOAuth2Authorization(oauth2Authorization);
    }

    /**
     * OAuth2Authorizationを生成
     *
     * @param oauth2Authorization
     *         oauth2 authorization
     *
     * @return OAuth2Authorization
     */
    private OAuth2Authorization generateOAuth2Authorization(final Oauth2Authorization oauth2Authorization) {
        final var client = registeredClientRepository.findById(oauth2Authorization.getRegisteredClientId());
        final var builder = OAuth2Authorization.withRegisteredClient(client);
        builder.id(oauth2Authorization.getId());
        builder.principalName(oauth2Authorization.getPrincipalName());
        final var map = (Map<String, Object>) convertHelper.convertJsonIntoObject(oauth2Authorization.getAttributes(),
                Map.class);
        map.forEach(builder::attribute);
        if (StringUtils.isNotBlank(oauth2Authorization.getAccessTokenValue())) {
            final var oauth2AccessToken = new OAuth2AccessToken( //
                    OAuth2AccessToken.TokenType.BEARER, //
                    oauth2Authorization.getAccessTokenValue(), //
                    oauth2Authorization.getAccessTokenIssuedAt().toInstant(OffsetDateTime.now().getOffset()), //
                    oauth2Authorization.getAccessTokenExpiresAt().toInstant(OffsetDateTime.now().getOffset()) //
            );
            builder.accessToken(oauth2AccessToken);
        }
        if (StringUtils.isNotBlank(oauth2Authorization.getRefreshTokenValue())) {
            final var refreshToken = new OAuth2RefreshToken( //
                    oauth2Authorization.getRefreshTokenValue(), //
                    oauth2Authorization.getRefreshTokenIssuedAt().toInstant(OffsetDateTime.now().getOffset()), //
                    oauth2Authorization.getRefreshTokenExpiresAt().toInstant(OffsetDateTime.now().getOffset()) //
            );
            builder.refreshToken(refreshToken);
        }

        if (StringUtils.isNotBlank(oauth2Authorization.getAuthorizationCodeValue())) {
            final var token = new OAuth2AuthorizationCode( //
                    oauth2Authorization.getAuthorizationCodeValue(), //
                    oauth2Authorization.getAuthorizationCodeIssuedAt().toInstant(OffsetDateTime.now().getOffset()), //
                    oauth2Authorization.getAuthorizationCodeExpiresAt().toInstant(OffsetDateTime.now().getOffset()) //
            );
            builder.token(token);
            builder.authorizationGrantType(new AuthorizationGrantType(oauth2Authorization.getAuthorizationGrantType()));
        }
        return builder.build();
    }
}
