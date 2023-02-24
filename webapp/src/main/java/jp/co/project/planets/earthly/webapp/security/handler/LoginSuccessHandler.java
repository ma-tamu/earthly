package jp.co.project.planets.earthly.webapp.security.handler;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.util.RequestUtils;

/**
 * login success handler
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger log = LoggerFactory.getLogger(LoginSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
            final Authentication authentication) throws IOException, ServletException {
        final var userInfoDto = (EarthlyUserInfoDto) authentication.getPrincipal();
        if (userInfoDto.tfa()) {
            log.info("login success");
            response.sendRedirect("%s/mfa".formatted(request.getContextPath()));
            return;
        }
        final var redirectUrlOptional = RequestUtils.getSavedRequestRedirectUrl();
        final var redirectUrl = redirectUrlOptional
                .orElse(String.format("%s/users/%s", request.getContextPath(), userInfoDto.id()));
        log.info("login success");
        response.sendRedirect(redirectUrl);
    }
}
