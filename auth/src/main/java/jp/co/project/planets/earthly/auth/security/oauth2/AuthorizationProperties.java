package jp.co.project.planets.earthly.auth.security.oauth2;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * authorization properties
 */
@Component
@ConfigurationProperties("auth.security.authorization")
public class AuthorizationProperties {

    /** issuer url */
    private String issuer;

    /**
     * get issuer
     * 
     * @return issuer
     */
    public String getIssuer() {
        return issuer;
    }

    /**
     * set issuer
     * 
     * @param issuer
     *            issuer
     */
    public void setIssuer(final String issuer) {
        this.issuer = issuer;
    }
}
