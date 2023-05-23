package jp.co.project.planets.earthly.auth.security.oauth2;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("auth.security.authorization")
public class AuthorizationProperties {

    private String issuer;

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(final String issuer) {
        this.issuer = issuer;
    }
}
