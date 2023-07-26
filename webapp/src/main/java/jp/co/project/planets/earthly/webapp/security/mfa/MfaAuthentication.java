package jp.co.project.planets.earthly.webapp.security.mfa;

import java.io.Serial;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;

public class MfaAuthentication extends AbstractAuthenticationToken {

    @Serial
    private static final long serialVersionUID = 1392145034409140583L;
    private final Authentication authentication;

    public MfaAuthentication(final Authentication authentication) {
        super(authentication.getAuthorities());
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
    public boolean isAuthenticated() {
        return false;
    }

    public Authentication getAuthentication() {
        return authentication;
    }
}
