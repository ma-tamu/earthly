package jp.co.project.planets.earthly.webapp.controller;

import static jp.co.project.planets.earthly.webapp.constant.ModelKey.*;

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

import jp.co.project.planets.earthly.webapp.controller.form.user.UserEntryForm;
import jp.co.project.planets.earthly.webapp.controller.form.user.UserSearchForm;
import jp.co.project.planets.earthly.webapp.controller.form.user.UserUpdateForm;
import jp.co.project.planets.earthly.webapp.exception.ForbiddenException;
import jp.co.project.planets.earthly.webapp.model.dto.UserSearchDto;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.service.UserService;

/**
 * user controller
 */
@Controller
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * ユーザーリスト検索
     *
     * @param userSearchForm
     *            ユーザー検索リクエスト
     * @param pageable
     *            ページャー
     * @param userInfoDto
     *            ユーザー情報
     * @return ユーザー情報
     */
    @GetMapping
    public ModelAndView search(@ModelAttribute final UserSearchForm userSearchForm,
            @PageableDefault final Pageable pageable, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var userSearchDto = new UserSearchDto(userSearchForm.loginId(), userSearchForm.name(),
                userSearchForm.company(), pageable.getOffset(), pageable.getPageSize(), pageable.getSort());
        final var userSearchResultDto = userService.search(userSearchDto, pageable, userInfoDto);
        final var modelAndView = new ModelAndView("users/list");
        modelAndView.addObject(userSearchResultDto);
        return modelAndView;
    }

    /**
     * 対象ユーザーを取得する。
     *
     * @param id
     *            ユーザーID
     * @param userInfoDto
     *            ユーザー情報
     * @return ユーザー詳細画面
     */
    @GetMapping("{userId}")
    public ModelAndView get(@PathVariable("userId") final String id, final Model model,
            @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var userEntity = userService.getById(id, userInfoDto);
        final var modelAndView = new ModelAndView("users/detail");
        final var userUpdateForm = new UserUpdateForm(userEntity.name(), userEntity.mail(), userEntity.language(),
                userEntity.timezone(), userEntity.company().name(), userEntity.company().id(), userEntity.lockout());
        modelAndView.addObject(userUpdateForm);
        modelAndView.addObject(userEntity);
        modelAndView.addAllObjects(model.asMap());
        return modelAndView;
    }

    /**
     * ユーザー登録
     *
     * @param model
     *            model
     * @param userInfoDto
     *            ユーザー情報
     * @return ユーザー登録
     */
    @GetMapping("entry")
    public ModelAndView entry(final Model model, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        userService.validateUserAddOperationPermission(userInfoDto);

        final var modelAndView = new ModelAndView("users/entry/index");
        modelAndView.addObject(UserEntryForm.EMPTY);
        modelAndView.addObject(READ_ONLY, false);
        modelAndView.addAllObjects(model.asMap());
        return modelAndView;
    }

    /**
     * ユーザー登録(入力チェック)
     *
     * @param userEntryForm
     *            ユーザー登録フォーム
     * @param bindingResult
     *            binding result
     * @param redirectAttributes
     *            redirect attributes
     * @param model
     *            model
     * @param userInfoDto
     *            ユーザー情報
     * @return ユーザー登録画面へリダイレクト
     */
    @PostMapping("entry")
    public ModelAndView entryConfirm(@ModelAttribute @Validated final UserEntryForm userEntryForm,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes, final Model model,
            @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var modelAndView = new ModelAndView("redirect:/users/entry");
        model.asMap().forEach(redirectAttributes::addFlashAttribute);
        if (bindingResult.hasErrors()) {
            return modelAndView;
        }
        try {
            userService.validateEntryOperation(userEntryForm.toDto(), userInfoDto);
            redirectAttributes.addFlashAttribute(READ_ONLY, true);
            return modelAndView;
        } catch (final ForbiddenException e) {
            redirectAttributes.addFlashAttribute(ERROR_CODE, e.getErrorCode());
            redirectAttributes.addFlashAttribute(MESSAGE_ARGS, e.getMessageKeyArgs());
            return new ModelAndView("redirect:/users/entry");
        }
    }

    /**
     * ユーザー登録
     *
     * @param userEntryForm
     *            ユーザー登録フォーム
     * @param bindingResult
     *            binding result
     * @param redirectAttributes
     *            redirect attributes
     * @param model
     *            model
     * @param userInfoDto
     *            ユーザー情報
     * @return ユーザー登録が行えた場合は、ユーザー詳細画面へ遷移。それ以外の場合は、ユーザー登録画面に遷移
     */
    @PostMapping("create")
    public ModelAndView create(@ModelAttribute @Validated final UserEntryForm userEntryForm,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes, final Model model,
            @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        if (bindingResult.hasErrors()) {
            model.asMap().forEach(redirectAttributes::addFlashAttribute);
            return new ModelAndView("redirect:/users/entry");
        }
        try {
            final var userId = userService.create(userEntryForm.toDto(), userInfoDto);
            return new ModelAndView(String.format("redirect:/users/%s", userId));
        } catch (final ForbiddenException e) {
            model.asMap().forEach(redirectAttributes::addFlashAttribute);
            redirectAttributes.addFlashAttribute(ERROR_CODE, e.getErrorCode());
            redirectAttributes.addFlashAttribute(MESSAGE_ARGS, e.getMessageKeyArgs());
            return new ModelAndView("redirect:/users/entry");
        }
    }

    /**
     * 編集確認
     * 
     * @param id
     *            ユーザーID
     * @param form
     *            編集フォーム
     * @param bindingResult
     *            binding result
     * @param model
     *            model
     * @param redirectAttributes
     *            redirect attributes
     * @param userInfoDto
     *            ユーザー情報
     * @return ユーザー詳細
     */
    @PostMapping("{userId}/edit")
    public ModelAndView edit(@PathVariable("userId") final String id,
            @ModelAttribute @Validated final UserUpdateForm form, final BindingResult bindingResult, final Model model,
            final RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var modelAndView = new ModelAndView("redirect:/users/%s".formatted(id));
        model.asMap().forEach(redirectAttributes::addFlashAttribute);
        redirectAttributes.addFlashAttribute("editMode", true);

        if (bindingResult.hasErrors()) {
            return modelAndView;
        }

        try {
            userService.validateUpdating(id, form.toDto(), userInfoDto);
            redirectAttributes.addFlashAttribute(READ_ONLY, true);
        } catch (final ForbiddenException e) {
            model.addAttribute(MESSAGE, e.getErrorCode().getMessageKey());
            model.addAttribute(MESSAGE_ARGS, e.getMessageKeyArgs());
        }
        return modelAndView;
    }

    /**
     * ユーザー更新
     * 
     * @param id
     *            ユーザーID
     * @param form
     *            編集フォーム
     * @param bindingResult
     *            binding result
     * @param model
     *            model
     * @param redirectAttributes
     *            redirect attributes
     * @param userInfoDto
     *            ユーザー情報
     * @return ユーザー詳細
     */
    @PostMapping("{userId}/update")
    public ModelAndView update(@PathVariable("userId") final String id,
            @ModelAttribute @Validated final UserUpdateForm form, final BindingResult bindingResult, final Model model,
            final RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var modelAndView = new ModelAndView("redirect:/users/%s".formatted(id));
        model.asMap().forEach(redirectAttributes::addFlashAttribute);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("editMode", true);
            return modelAndView;
        }

        try {
            final var message = userService.update(id, form.toDto(), userInfoDto);
            redirectAttributes.addFlashAttribute(SUCCESS, message);
        } catch (final ForbiddenException e) {
            model.addAttribute(MESSAGE, e.getErrorCode().getMessageKey());
            model.addAttribute(MESSAGE_ARGS, e.getMessageKeyArgs());
        }
        return modelAndView;
    }

    /**
     * ユーザー削除
     * 
     * @param id
     *            ユーザーID
     * @param redirectAttributes
     *            redirect attributes
     * @param userInfoDto
     *            ユーザー情報
     * @return ユーザーリスト
     */
    public ModelAndView delete(@PathVariable("userId") final String id, final RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        try {
            final var message = userService.delete(id, userInfoDto);
            redirectAttributes.addFlashAttribute(SUCCESS, message);
            final var modelAndView = new ModelAndView("redirect:/users");
            return modelAndView;
        } catch (final ForbiddenException e) {
            final var modelAndView = new ModelAndView("redirect:/users/%s".formatted(id));
            return modelAndView;
        }
    }
}
