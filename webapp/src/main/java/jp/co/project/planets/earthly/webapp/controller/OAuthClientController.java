package jp.co.project.planets.earthly.webapp.controller;

import java.util.Collections;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.project.planets.earthly.webapp.controller.form.client.OAuthClientSearchForm;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;

/**
 * OAuthクライアントコントローラー
 */
@Controller
@RequestMapping("clients")
public class OAuthClientController {

    @GetMapping
    public ModelAndView search(@ModelAttribute final OAuthClientSearchForm oauthClientSearchForm,
            @PageableDefault final Pageable pageable, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var modelAndView = new ModelAndView("clients/index");
        modelAndView.addObject(new PageImpl<String>(Collections.emptyList()));
        return modelAndView;
    }
}
