package jp.co.project.planets.earthly.webapp.security.mfa;

import java.io.Serial;
import java.util.Collections;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.CredentialsContainer;

public class MultiFactorAuthentication extends AbstractAuthenticationToken {

    @Serial
    private static final long serialVersionUID = -4605201825077228013L;
    private final Authentication authentication;

    public MultiFactorAuthentication(final Authentication authentication) {
        super(Collections.emptyList());
        this.authentication = authentication;
    }

    @Override
    public Object getCredentials() {
        return authentication.getCredentials();
    }

    @Override
    public Object getPrincipal() {
        return authentication.getPrincipal();
    }

    @Override
    public void eraseCredentials() {
        if (authentication instanceof CredentialsContainer) {
            ((CredentialsContainer) authentication).eraseCredentials();
        }
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    public Authentication getPrimary() {
        return this.authentication;
    }

}
