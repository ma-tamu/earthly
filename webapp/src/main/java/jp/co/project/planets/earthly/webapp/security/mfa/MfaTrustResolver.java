package jp.co.project.planets.earthly.webapp.security.mfa;

import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

public class MfaTrustResolver implements AuthenticationTrustResolver {

    private final AuthenticationTrustResolver delegate = new AuthenticationTrustResolverImpl();

    @Override
    public boolean isAnonymous(final Authentication authentication) {
        if (delegate.isAnonymous(authentication)) {
            return true;
        }
        return authentication instanceof MfaAuthentication;
    }

    @Override
    public boolean isRememberMe(final Authentication authentication) {
        return delegate.isRememberMe(authentication);
    }
}
