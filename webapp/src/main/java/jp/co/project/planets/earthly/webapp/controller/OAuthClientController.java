package jp.co.project.planets.earthly.webapp.controller;

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
import jp.co.project.planets.earthly.webapp.service.OAuthClientService;

/**
 * OAuthクライアントコントローラー
 */
@Controller
@RequestMapping("clients")
public class OAuthClientController {

    private final OAuthClientService oauthClientService;

    public OAuthClientController(final OAuthClientService oauthClientService) {
        this.oauthClientService = oauthClientService;
    }

    /**
     * OAuthクライアント検索
     *
     * @param oauthClientSearchForm
     *            OAuthクライアント検索FORM
     * @param pageable
     *            ページャー
     * @param userInfoDto
     *            ユーザー情報
     * @return OAuthクライアント検索結果
     */
    @GetMapping
    public ModelAndView search(@ModelAttribute final OAuthClientSearchForm oauthClientSearchForm,
            @PageableDefault final Pageable pageable, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        final var oauthClients = oauthClientService.search(oauthClientSearchForm.name(), pageable, userInfoDto);
        final var modelAndView = new ModelAndView("clients/index");
        modelAndView.addObject(oauthClients);
        return modelAndView;
    }
}
