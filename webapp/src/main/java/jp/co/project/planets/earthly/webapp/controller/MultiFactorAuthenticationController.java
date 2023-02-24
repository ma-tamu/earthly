package jp.co.project.planets.earthly.webapp.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.service.AuthenticationService;
import jp.co.project.planets.earthly.webapp.util.RequestUtils;

/**
 * multi factor authentication controller
 */
@Controller
@RequestMapping("mfa")
public class MultiFactorAuthenticationController {

    private final AuthenticationService authenticationService;

    public MultiFactorAuthenticationController(final AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public ModelAndView index(@AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        return new ModelAndView("mfa");
    }

    @PostMapping
    public ModelAndView verify(final String code, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final boolean successful = authenticationService.verify(code, userInfoDto);
        if (!successful) {
            return new ModelAndView("redirect:/mfa");
        }
        authenticationService.updateSecurityContext(userInfoDto);
        final var requestRedirectUrlOptional = RequestUtils.getSavedRequestRedirectUrl();
        final var redirectUrl = requestRedirectUrlOptional
                .map(s -> StringUtils.removeStart(s, RequestUtils.getRequest().getContextPath()))
                .orElse("/users/%s".formatted(userInfoDto.id()));
        return new ModelAndView("redirect:" + redirectUrl);
    }
}
