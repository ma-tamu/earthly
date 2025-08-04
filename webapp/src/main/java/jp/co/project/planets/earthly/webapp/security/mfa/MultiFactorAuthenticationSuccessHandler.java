package jp.co.project.planets.earthly.webapp.security.mfa;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.security.notice.EmphasisNoticeAuthentication;
import jp.co.project.planets.earthly.webapp.security.notice.EmphasisNoticeSuccessHandler;

public class MultiFactorAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final AuthenticationSuccessHandler primarySuccessHandler;
    private final AuthenticationSuccessHandler secondarySuccessHandler;
    private final AuthenticationSuccessHandler thirdSuccessHandler;

    private static final Logger log = LoggerFactory.getLogger(MultiFactorAuthenticationSuccessHandler.class);

    public MultiFactorAuthenticationSuccessHandler(final String secondAuthUrl,
        final AuthenticationSuccessHandler primarySuccessHandler) {
        this.primarySuccessHandler = primarySuccessHandler;
        this.secondarySuccessHandler = new SimpleUrlAuthenticationSuccessHandler(secondAuthUrl);
        this.thirdSuccessHandler = new EmphasisNoticeSuccessHandler(primarySuccessHandler);

    }

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
        final Authentication authentication) throws IOException, ServletException {
        final var userInfoDto = (EarthlyUserInfoDto) authentication.getPrincipal();

        if (userInfoDto.account().multiFactor().enabled()) {
            SecurityContextHolder.getContext().setAuthentication(new MultiFactorAuthentication(authentication));
            this.secondarySuccessHandler.onAuthenticationSuccess(request, response, authentication);
        } else {
            if (userInfoDto.account().emphasis().showEmphasisNotice()) {
                SecurityContextHolder.getContext().setAuthentication(new EmphasisNoticeAuthentication(authentication));
                this.thirdSuccessHandler.onAuthenticationSuccess(request, response, authentication);
            } else {
                this.primarySuccessHandler.onAuthenticationSuccess(request, response, authentication);
            }
        }

    }
}
