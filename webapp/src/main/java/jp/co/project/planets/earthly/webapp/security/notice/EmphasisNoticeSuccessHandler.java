package jp.co.project.planets.earthly.webapp.security.notice;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;

public class EmphasisNoticeSuccessHandler implements AuthenticationSuccessHandler {

    private final AuthenticationSuccessHandler primarySuccessHandler;
    private final AuthenticationSuccessHandler tertiarySuccessHandler;

    public EmphasisNoticeSuccessHandler(final AuthenticationSuccessHandler primarySuccessHandler) {
        this.primarySuccessHandler = primarySuccessHandler;
        this.tertiarySuccessHandler = new SimpleUrlAuthenticationSuccessHandler("/notices/emphasis");
    }

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
        final Authentication authentication) throws IOException, ServletException {
        final var userInfoDto = (EarthlyUserInfoDto) authentication.getPrincipal();

        if (userInfoDto.account().emphasis().showEmphasisNotice()) {
            SecurityContextHolder.getContext().setAuthentication(new EmphasisNoticeAuthentication(authentication));
            this.tertiarySuccessHandler.onAuthenticationSuccess(request, response, authentication);
        } else {
            this.primarySuccessHandler.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
