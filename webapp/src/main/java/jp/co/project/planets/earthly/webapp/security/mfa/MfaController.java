package jp.co.project.planets.earthly.webapp.security.mfa;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.service.MfaService;

/**
 * mfa controller
 */
@Controller
@RequestMapping("mfa")
public class MfaController {

    private final MfaService mfaService;

    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;

    public MfaController(final MfaService mfaService, final AuthenticationSuccessHandler successHandler,
            final AuthenticationFailureHandler failureHandler) {
        this.mfaService = mfaService;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }

    @GetMapping
    public ModelAndView index() {
        return new ModelAndView("mfa");
    }

    @PostMapping
    public void verify(final String code, final HttpServletRequest request, final HttpServletResponse response,
            final MfaAuthentication authentication) throws ServletException, IOException {
        final var firstAuthentication = authentication.getAuthentication();
        final var userInfoDto = (EarthlyUserInfoDto) firstAuthentication.getPrincipal();
        final boolean successful = mfaService.verify(code, userInfoDto);
        if (successful) {
            mfaService.updateSecurityContext(userInfoDto);
            SecurityContextHolder.getContext().setAuthentication(firstAuthentication);
            successHandler.onAuthenticationSuccess(request, response, firstAuthentication);
        } else {
            failureHandler.onAuthenticationFailure(request, response,
                    new BadCredentialsException("bat credentials mfa code"));
        }
    }
}
