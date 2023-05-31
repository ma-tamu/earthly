package jp.co.project.planets.earthly.auth.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import jp.co.project.planets.earthly.auth.security.service.OidcUserInfoService;

@Configuration
public class IdTokenConfig {

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer(final OidcUserInfoService userInfoService) {
        return context -> {
            if (StringUtils.equals(OidcParameterNames.ID_TOKEN, context.getTokenType().getValue())) {
                final var oidcUserInfo = userInfoService.loadUser(context.getPrincipal().getName());
                context.getClaims().claims(claims -> claims.putAll(oidcUserInfo.getClaims()));
            }
        };
    }
}
