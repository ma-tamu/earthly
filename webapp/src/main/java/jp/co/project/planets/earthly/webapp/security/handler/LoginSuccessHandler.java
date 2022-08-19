package jp.co.project.planets.earthly.webapp.security.handler;

import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * login success handler
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger log = LoggerFactory.getLogger(LoginSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication) throws IOException, ServletException {
        final var userInfoDto = (EarthlyUserInfoDto) authentication.getPrincipal();
        final var session = request.getSession();
        final var redirectUrl = Optional.ofNullable(
                (DefaultSavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST")).map(v -> {
            final var builder = UriComponentsBuilder.fromPath(v.getRequestURI());
            v.getParameterMap().forEach(builder::queryParam);
            return builder.build().toString();
        }).orElse(String.format("%s/users/%s", request.getContextPath(), userInfoDto.id()));
        log.info("login success");
        response.sendRedirect(redirectUrl);
    }
}
