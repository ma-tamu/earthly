package jp.co.project.planets.earthly.webapp.security.mfa;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.co.project.planets.earthly.webapp.constant.ModelKey;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.util.RequestUtils;

/**
 * login success handler
 */
public class MfaAuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

    private static final Logger log = LoggerFactory.getLogger(MfaAuthenticationHandler.class);

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
            final Authentication authentication) throws IOException, ServletException {
        final var userInfoDto = (EarthlyUserInfoDto) authentication.getPrincipal();
        if (userInfoDto.tfa()) {
            log.info("login success redirect to {}", "/mfa");
            SecurityContextHolder.getContext().setAuthentication(new MfaAuthentication(authentication));
            final var authenticationSuccessHandler = new SimpleUrlAuthenticationSuccessHandler("/mfa");
            authenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);
            return;
        }
        final var redirectUrlOptional = RequestUtils.getSavedRequestRedirectUrl();
        final var redirectUrl = redirectUrlOptional.map(s -> s.replaceAll(request.getContextPath(), StringUtils.EMPTY))
                .orElse(String.format("/users/%s", userInfoDto.id()));
        log.info("login success redirect to {}", redirectUrl);
        final var authenticationSuccessHandler = new SimpleUrlAuthenticationSuccessHandler(redirectUrl);
        authenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);
    }

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response,
            final AuthenticationException exception) throws IOException, ServletException {
        request.getSession().setAttribute(ModelKey.EXCEPTION, exception);
        response.sendRedirect("./login");
    }
}
