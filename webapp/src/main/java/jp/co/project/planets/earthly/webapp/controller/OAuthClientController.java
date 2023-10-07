package jp.co.project.planets.earthly.webapp.controller;

import static jp.co.project.planets.earthly.webapp.constant.ModelKey.*;

import java.util.Collections;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.project.planets.earthly.webapp.constant.ViewName;
import jp.co.project.planets.earthly.webapp.controller.form.client.OAuthClientEntryForm;
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

    /**
     * OAuthクライアント登録
     *
     * @param model
     *            model
     * @param userInfoDto
     *            ユーザー情報
     * @return OAuthクライアント登録
     */
    @GetMapping("entry")
    public ModelAndView entry(final Model model, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        oauthClientService.validateEntryPermission(userInfoDto);

        final var modelAndView = new ModelAndView("clients/entry/index");
        modelAndView.addObject(new OAuthClientEntryForm(null, Collections.emptyList()));
        modelAndView.addObject(READ_ONLY, false);
        modelAndView.addAllObjects(model.asMap());
        return modelAndView;
    }

    /**
     * OAuthクライアント登録検証
     *
     * @param oauthClientEntryForm
     *            OAuthクライアント登録FROM
     * @param bindingResult
     *            バリデーション結果
     * @param redirectAttributes
     *            redirect attributes
     * @param model
     *            model
     * @param userInfoDto
     *            ユーザー情報
     * @return OAuthクライアント登録
     */
    @PostMapping("entry")
    public ModelAndView entryConfirm(@ModelAttribute @Validated final OAuthClientEntryForm oauthClientEntryForm,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes, final Model model,
            @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        final var modelAndView = new ModelAndView(ViewName.REDIRECT_CLIENT_ENTRY);
        model.asMap().forEach(redirectAttributes::addFlashAttribute);
        if (bindingResult.hasErrors()) {
            return modelAndView;
        }

        oauthClientService.validateEntryOperation(oauthClientEntryForm.toDto(), userInfoDto);
        redirectAttributes.addFlashAttribute(READ_ONLY, true);
        return modelAndView;
    }

    /**
     * OAuthクライアント登録
     *
     * @param oauthClientEntryForm
     *            OAuthクライアント登録FROM
     * @param bindingResult
     *            バリデーション結果
     * @param redirectAttributes
     *            redirect attributes
     * @param model
     *            model
     * @param userInfoDto
     *            ユーザー情報
     * @return OAuthクライアント結果
     */
    @PostMapping("create")
    public ModelAndView create(@ModelAttribute @Validated final OAuthClientEntryForm oauthClientEntryForm,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes, final Model model,
            @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        if (bindingResult.hasErrors()) {
            final var modelAndView = new ModelAndView(ViewName.REDIRECT_CLIENT_ENTRY);
            model.asMap().forEach(redirectAttributes::addFlashAttribute);
            return modelAndView;
        }

        final var id = oauthClientService.create(oauthClientEntryForm.toDto(), userInfoDto);
        return new ModelAndView(ViewName.REDIRECT_CLIENT_DETAIL.formatted(id));
    }
}
