package jp.co.project.planets.earthly.webservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;

/**
 * open api config
 */
@Configuration
public class OpenApiConfig {

    private final ClientRegistrationRepository clientRegistrationRepository;

    public OpenApiConfig(final ClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @Bean
    public OpenAPI openAPI() {
        final var clientRegistration = clientRegistrationRepository.findByRegistrationId("earthly");
        final var providerDetails = clientRegistration.getProviderDetails();
        final var scopes = new Scopes();
        clientRegistration.getScopes().forEach(scope -> scopes.addString(scope, scope));
        final var authorizationCode = new OAuthFlow().authorizationUrl(providerDetails.getAuthorizationUri())
                .tokenUrl(providerDetails.getTokenUri()).scopes(scopes);
        final var flows = new OAuthFlows().authorizationCode(authorizationCode);
        final var securityScheme = new SecurityScheme().type(SecurityScheme.Type.OAUTH2).scheme("oauth2").flows(flows);
        final var securityRequirement = new SecurityRequirement().addList("OAuth2");
        final var components = new Components().addSecuritySchemes("OAuth2", securityScheme);
        final var info = new Info().title("Earthly API").version("0.0.1");
        return new OpenAPI().info(info).components(components).addSecurityItem(securityRequirement);
    }

}
