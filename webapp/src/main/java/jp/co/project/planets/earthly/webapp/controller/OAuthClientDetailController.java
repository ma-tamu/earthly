package jp.co.project.planets.earthly.webapp.controller;

import static jp.co.project.planets.earthly.webapp.constant.ModelKey.*;
import static jp.co.project.planets.earthly.webapp.constant.ViewName.*;

import java.util.Collections;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.project.planets.earthly.schema.db.entity.User;
import jp.co.project.planets.earthly.webapp.constant.MessageKey;
import jp.co.project.planets.earthly.webapp.constant.ViewName;
import jp.co.project.planets.earthly.webapp.controller.form.client.OAuthClientEditForm;
import jp.co.project.planets.earthly.webapp.controller.form.client.OAuthClientLogoutRedirectUriRemoveForm;
import jp.co.project.planets.earthly.webapp.controller.form.client.OAuthClientLogoutRedirectUriSearchForm;
import jp.co.project.planets.earthly.webapp.controller.form.client.OAuthClientLogoutRedirectUrlAddForm;
import jp.co.project.planets.earthly.webapp.controller.form.client.OAuthClientLogoutRedirectUrlSearchForm;
import jp.co.project.planets.earthly.webapp.controller.form.client.OAuthClientManagementUserAssignForm;
import jp.co.project.planets.earthly.webapp.controller.form.client.OAuthClientManagementUserSearchForm;
import jp.co.project.planets.earthly.webapp.controller.form.client.OAuthClientManagementUserUnassignForm;
import jp.co.project.planets.earthly.webapp.controller.form.client.OAuthClientNotAssignUserSearchForm;
import jp.co.project.planets.earthly.webapp.controller.form.client.OAuthClientRedirectUriRemoveForm;
import jp.co.project.planets.earthly.webapp.controller.form.client.OAuthClientRedirectUrlAddForm;
import jp.co.project.planets.earthly.webapp.controller.form.client.OAuthClientRedirectUrlSearchForm;
import jp.co.project.planets.earthly.webapp.emuns.ErrorCode;
import jp.co.project.planets.earthly.webapp.exception.BadRequestException;
import jp.co.project.planets.earthly.webapp.exception.ForbiddenException;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.service.OAuthClientService;

/**
 * OAuthクライアント詳細コントローラー
 */
@Controller
@RequestMapping("clients/{id}")
public class OAuthClientDetailController {

    private final OAuthClientService oauthClientService;

    public OAuthClientDetailController(final OAuthClientService oauthClientService) {
        this.oauthClientService = oauthClientService;
    }

    /**
     * OAuthクライアント詳細
     *
     * @param id
     *            OAuthクライアントID
     * @param model
     *            model
     * @param userInfoDto
     *            ユーザー情報
     * @return OAuthクライアント詳細
     */
    @GetMapping
    public ModelAndView index(@PathVariable("id") final String id, final Model model,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var oauthClientDetailDto = oauthClientService.get(id, userInfoDto.account());
        final var modelAndView = new ModelAndView("clients/detail");
        final var oauthClientDetailEntity = oauthClientDetailDto.oauthClientDetailEntity();
        modelAndView.addObject(oauthClientDetailEntity);
        modelAndView.addObject(REDIRECT_URL_PAGE, oauthClientDetailDto.redirectUrlPage());
        modelAndView.addObject(LOGOUT_REDIRECT_URL_PAGE, oauthClientDetailDto.logoutRedirectUrlPage());
        modelAndView.addObject(MANAGEMENT_USER_PAGE, oauthClientDetailDto.managementUserPage());
        modelAndView.addObject("canEditableClient", oauthClientDetailDto.canEditableClient());
        modelAndView.addObject("unassignedManagementUserPage", new PageImpl<User>(Collections.emptyList()));
        modelAndView.addObject(new OAuthClientRedirectUrlSearchForm(null, false));
        modelAndView.addObject(new OAuthClientRedirectUrlAddForm(null));
        modelAndView.addObject(new OAuthClientLogoutRedirectUriSearchForm(null, false));
        modelAndView.addObject(new OAuthClientLogoutRedirectUrlAddForm(null));
        modelAndView.addObject(new OAuthClientLogoutRedirectUrlSearchForm(null, false));
        modelAndView.addObject(new OAuthClientManagementUserSearchForm(null, null, false));
        final var oauthClientEditForm = new OAuthClientEditForm(oauthClientDetailEntity.name(),
                oauthClientDetailEntity.scopes());
        modelAndView.addObject(oauthClientEditForm);
        modelAndView.addAllObjects(model.asMap());
        return modelAndView;
    }

    /**
     * OAuthクライアント編集検証
     *
     * @param id
     *            OAuthクライアントID
     * @param oauthClientEditForm
     *            OAuthクライアント編集FROM
     * @param bindingResult
     *            binding result
     * @param redirectAttributes
     *            redirect attributes
     * @param model
     *            model
     * @param userInfoDto
     *            ユーザー情報
     * @return OAuthクライアント編集
     */
    @PostMapping("edit")
    public ModelAndView edit(@PathVariable("id") final String id,
        @ModelAttribute @Validated final OAuthClientEditForm oauthClientEditForm,
        final BindingResult bindingResult, final RedirectAttributes redirectAttributes, final Model model,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        final var modelAndView = new ModelAndView(ViewName.REDIRECT_CLIENT_DETAIL.formatted(id));
        redirectAttributes.addFlashAttribute("editMode", true);
        model.asMap().forEach(redirectAttributes::addFlashAttribute);

        if (bindingResult.hasErrors()) {
            return modelAndView;
        }

        try {
            oauthClientService.validateEditPermission(id, userInfoDto.account());
            redirectAttributes.addFlashAttribute(READ_ONLY, true);
        } catch (final ForbiddenException e) {
            model.addAttribute(MESSAGE, e.getErrorCode().getMessageKey());
            model.addAttribute(MESSAGE_ARGS, e.getMessageKeyArgs());
        }
        return modelAndView;
    }

    @PostMapping("update")
    public ModelAndView update(@PathVariable("id") final String id,
        @ModelAttribute @Validated final OAuthClientEditForm oauthClientEditForm,
        final BindingResult bindingResult, final RedirectAttributes redirectAttributes, final Model model,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        final var modelAndView = new ModelAndView(ViewName.REDIRECT_CLIENT_DETAIL.formatted(id));
        model.asMap().forEach(redirectAttributes::addFlashAttribute);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("editMode", true);
            return modelAndView;
        }

        try {
            final var message = oauthClientService.update(id, oauthClientEditForm.toDto(), userInfoDto.account());
            redirectAttributes.addFlashAttribute(SUCCESS, message);
        } catch (final ForbiddenException e) {
            model.addAttribute(MESSAGE, e.getErrorCode().getMessageKey());
            model.addAttribute(MESSAGE_ARGS, e.getMessageKeyArgs());
        }
        return modelAndView;
    }

    /**
     * OAuthクライアントリダイレクトURL検索
     *
     * @param id
     *            OAuthクライアントID
     * @param oauthClientRedirectUrlSearchForm
     *            OAuthクライアントリダイレクトURL検索FORM
     * @param pageable
     *            ページャー
     * @param userInfoDto
     *            ユーザー情報
     * @return 検索結果
     */
    @GetMapping("redirect-uris")
    public ModelAndView searchRedirectUrl(@PathVariable("id") final String id,
        final OAuthClientRedirectUrlSearchForm oauthClientRedirectUrlSearchForm,
        @PageableDefault final Pageable pageable, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        final var oauthClientRedirectUrlPage = oauthClientService.searchRedirectUrl(id,
                oauthClientRedirectUrlSearchForm.redirectUri(), pageable, userInfoDto.account());
        return new ModelAndView(CLIENT_DETAIL_REDIRECT_URL_PAGE)
                .addObject(REDIRECT_URL_PAGE, oauthClientRedirectUrlPage);
    }

    /**
     * OAuthクライアントリダイレクトURL登録
     *
     * @param id
     *            OAuthクライアントID
     * @param oauthClientRedirectUrlAddForm
     *            OAuthクライアントリダイレクト登録FORM
     * @param bindingResult
     *            binding result
     * @param redirectAttributes
     *            redirect attributes
     * @param model
     *            model
     * @param userInfoDto
     *            ユーザー情報
     * @return 確認結果
     */
    @PostMapping("redirect-uris/add")
    public ModelAndView addRedirectUri(@PathVariable("id") final String id,
        @Validated final OAuthClientRedirectUrlAddForm oauthClientRedirectUrlAddForm,
        final BindingResult bindingResult, final RedirectAttributes redirectAttributes, final Model model,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        final var modelAndView = new ModelAndView(ViewName.REDIRECT_CLIENT_DETAIL.formatted(id));
        model.asMap().forEach(redirectAttributes::addFlashAttribute);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("isAddRedirectUri", true);
            return modelAndView;
        }
        oauthClientService.addRedirectUrl(id, oauthClientRedirectUrlAddForm.redirectUri(), userInfoDto.account());
        return modelAndView;
    }

    /**
     * OAuthクライアントリダイレクトURI削除
     *
     * @param id
     *            OAuthクライアントID
     * @param oauthClientRedirectUriRemoveForm
     *            OAuthクライアントリダイレクトURI削除FROM
     * @param bindingResult
     *            binding result
     * @param userInfoDto
     *            ユーザー情報
     * @return 削除結果
     */
    @PostMapping("redirect-uris/remove")
    public ModelAndView removeRedirectUri(@PathVariable("id") final String id,
        @ModelAttribute @Validated final OAuthClientRedirectUriRemoveForm oauthClientRedirectUriRemoveForm,
        final BindingResult bindingResult, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView(TOAST_DANGER).addObject(MESSAGE, ErrorCode.EWA5XX999.getMessageKey());
        }
        try {
            oauthClientService.removeRedirectUri(id, oauthClientRedirectUriRemoveForm.redirectUriIds(),
                    userInfoDto.account());
            return new ModelAndView(TOAST_SUCCESS).addObject(MESSAGE, MessageKey.DELETE_SUCCESS);
        } catch (final BadRequestException e) {
            return new ModelAndView(TOAST_DANGER).addObject(MESSAGE, e.getErrorCode().getMessageKey())
                    .addObject(MESSAGE_ARGS, e.getMessageKeyArgs());
        }
    }

    /**
     * OAuthクライアントリダイレクトURL検索
     *
     * @param id
     *            OAuthクライアントID
     * @param oauthClientLogoutRedirectUrlSearchForm
     *            OAuthクライアントログアウトリダイレクトURL検索FORM
     * @param pageable
     *            ページャー
     * @param userInfoDto
     *            ユーザー情報
     * @return 検索結果
     */
    @GetMapping("logout-redirect-uris")
    public ModelAndView searchLogoutRedirectUrl(@PathVariable("id") final String id,
        final OAuthClientLogoutRedirectUrlSearchForm oauthClientLogoutRedirectUrlSearchForm,
        @PageableDefault final Pageable pageable, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        final var oauthClientRedirectUrlPage = oauthClientService.searchLogoutRedirectUrl(id,
                oauthClientLogoutRedirectUrlSearchForm.logoutRedirectUrl(), pageable, userInfoDto.account());
        return new ModelAndView(CLIENT_DETAIL_LOGOUT_REDIRECT_URL_PAGE)
                .addObject(LOGOUT_REDIRECT_URL_PAGE, oauthClientRedirectUrlPage);
    }

    /**
     * OAuthクライアントログアウトリダイレクトURI追加
     * 
     * @param id
     *            OAuthクライアントID
     * @param oauthClientLogoutRedirectUrlAddForm
     *            OAuthクライアントログアウトリダイレクトURI登録FORM
     * @param bindingResult
     *            binding result
     * @param redirectAttributes
     *            redirect attributes
     * @param model
     *            model
     * @param userInfoDto
     *            ユーザー情報
     * @return 登録結果
     */
    @PostMapping("logout-redirect-uris/add")
    public ModelAndView addLogoutRedirectUri(@PathVariable("id") final String id,
        @Validated final OAuthClientLogoutRedirectUrlAddForm oauthClientLogoutRedirectUrlAddForm,
        final BindingResult bindingResult, final RedirectAttributes redirectAttributes, final Model model,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        final var modelAndView = new ModelAndView(ViewName.REDIRECT_CLIENT_DETAIL.formatted(id));
        model.asMap().forEach(redirectAttributes::addFlashAttribute);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("isAddLogoutRedirectUri", true);
            return modelAndView;
        }
        oauthClientService.addLogoutRedirectUrl(id, oauthClientLogoutRedirectUrlAddForm.redirectUri(),
                userInfoDto.account());
        return modelAndView;
    }

    /**
     * OAuthクライアントログアウトリダイレクトURI削除
     *
     * @param id
     *            OAuthクライアントID
     * @param oauthClientLogoutRedirectUriRemoveForm
     *            OAuthクライアントリダイレクトURI削除FROM
     * @param bindingResult
     *            binding result
     * @param userInfoDto
     *            ユーザー情報
     * @return 削除結果
     */
    @PostMapping("logout-redirect-uris/remove")
    public ModelAndView removeLogoutRedirectUri(@PathVariable("id") final String id,
        @ModelAttribute @Validated final OAuthClientLogoutRedirectUriRemoveForm oauthClientLogoutRedirectUriRemoveForm,
        final BindingResult bindingResult, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView(TOAST_DANGER).addObject(MESSAGE, ErrorCode.EWA5XX999.getMessageKey());
        }
        try {
            oauthClientService.removeLogoutRedirectUri(id,
                    oauthClientLogoutRedirectUriRemoveForm.logoutRedirectUriIds(),
                    userInfoDto.account());
            return new ModelAndView(TOAST_SUCCESS).addObject(MESSAGE, MessageKey.DELETE_SUCCESS);
        } catch (final BadRequestException e) {
            return new ModelAndView(TOAST_DANGER).addObject(MESSAGE, e.getErrorCode().getMessageKey())
                    .addObject(MESSAGE_ARGS, e.getMessageKeyArgs());
        }
    }

    /**
     * OAuthクライアント管理者リスト検索
     *
     * @param id
     *            OAuthクライアントID
     * @param oauthClientManagementUserSearchForm
     *            OAuthクライアント管理者検索FROM
     * @param pageable
     *            ページャー
     * @param userInfoDto
     *            ユーザー情報
     * @return 検索結果
     */
    @GetMapping("management-users")
    public ModelAndView searchManagementUser(@PathVariable("id") final String id,
        final OAuthClientManagementUserSearchForm oauthClientManagementUserSearchForm,
        @PageableDefault final Pageable pageable, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var oauthClientManagementList = oauthClientService.searchManagementUser(id,
                oauthClientManagementUserSearchForm.userName(), oauthClientManagementUserSearchForm.companyName(),
                pageable, userInfoDto.account());
        return new ModelAndView(CLIENT_DETAIL_MANAGEMENT_USER_PAGE).addObject(MANAGEMENT_USER_PAGE,
                oauthClientManagementList);
    }

    /**
     * OAuthクライアント管理者未割り当てユーザー検索
     * 
     * @param id
     *            OAuthクライアントID
     * @param oauthClientNotAssignUserSearchForm
     *            OAuthクライアント管理者未割り当て検索FORM
     * @param pageable
     *            ページャー
     * @param userInfoDto
     *            ユーザー情報
     * @return 検索結果
     */
    @GetMapping("management-users/not-assigns")
    public ModelAndView searchNotAssignUser(@PathVariable("id") final String id,
        final OAuthClientNotAssignUserSearchForm oauthClientNotAssignUserSearchForm,
        @PageableDefault final Pageable pageable, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var oauthClientManagementUserEntityPage = oauthClientService.searchNotAssignUserUser(id,
                oauthClientNotAssignUserSearchForm.loginId(), oauthClientNotAssignUserSearchForm.userName(),
                oauthClientNotAssignUserSearchForm.companyName(), pageable, userInfoDto.account());
        return new ModelAndView(CLIENT_DETAIL_UNASSIGNED_MANAGEMENT_USER_PAGE) //
                .addObject(UNASSIGNED_MANAGEMENT_USER_PAGE, oauthClientManagementUserEntityPage);
    }

    /**
     * OAuthクライアント管理者割り当て
     * 
     * @param id
     *            OAuthクライアントID
     * @param oauthClientManagementUserAssignForm
     *            OAuthクライアント管理者割り当てFROM
     * @param bindingResult
     *            binding result
     * @param userInfoDto
     *            ユーザー情報
     * @return 割り当て結果
     */
    @PostMapping("management-users/assigns")
    public ModelAndView assignManagementUser(@PathVariable("id") final String id,
        @ModelAttribute @Validated final OAuthClientManagementUserAssignForm oauthClientManagementUserAssignForm,
        final BindingResult bindingResult, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView(TOAST_DANGER).addObject(MESSAGE, ErrorCode.EWA5XX999.getMessageKey());
        }
        try {
            oauthClientService.assignUser(id, oauthClientManagementUserAssignForm.userId(), userInfoDto.account());
            return new ModelAndView(TOAST_SUCCESS).addObject(MESSAGE, MessageKey.ASSIGN_SUCCESS);
        } catch (final BadRequestException e) {
            return new ModelAndView(TOAST_DANGER).addObject(MESSAGE, e.getErrorCode().getMessageKey())
                    .addObject(MESSAGE_ARGS, e.getMessageKeyArgs());
        }
    }

    /**
     * OAuthクライアント管理者解除
     * 
     * @param id
     *            OAuthクライアントID
     * @param oauthClientManagementUserUnassignForm
     *            OAuthクライアント管理者解除FORM
     * @param bindingResult
     *            binding result
     * @param userInfoDto
     *            ユーザー情報
     * @return 解除結果
     */
    @PostMapping("management-users/unassigns")
    public ModelAndView unassignManagementUser(@PathVariable("id") final String id,
        @ModelAttribute @Validated final OAuthClientManagementUserUnassignForm oauthClientManagementUserUnassignForm,
        final BindingResult bindingResult, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView(TOAST_DANGER).addObject(MESSAGE, ErrorCode.EWA5XX999.getMessageKey());
        }
        try {
            oauthClientService.unassignUser(id, oauthClientManagementUserUnassignForm.userId(), userInfoDto.account());
            return new ModelAndView(TOAST_SUCCESS).addObject(MESSAGE, MessageKey.UNASSIGN_SUCCESS);
        } catch (final BadRequestException e) {
            return new ModelAndView(TOAST_DANGER).addObject(MESSAGE, e.getErrorCode().getMessageKey())
                    .addObject(MESSAGE_ARGS, e.getMessageKeyArgs());
        }
    }
}
