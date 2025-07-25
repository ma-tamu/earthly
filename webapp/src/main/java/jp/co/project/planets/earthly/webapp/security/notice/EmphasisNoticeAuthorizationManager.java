package jp.co.project.planets.earthly.webapp.security.notice;

import java.util.function.Supplier;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

public class EmphasisNoticeAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Override
    public AuthorizationDecision check(final Supplier<Authentication> authentication,
        final RequestAuthorizationContext object) {
        return new AuthorizationDecision(authentication.get() instanceof EmphasisNoticeAuthentication);
    }
}
