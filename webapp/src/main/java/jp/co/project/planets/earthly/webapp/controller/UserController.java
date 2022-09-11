package jp.co.project.planets.earthly.webapp.controller;

import jp.co.project.planets.earthly.webapp.controller.form.UserEntryForm;
import jp.co.project.planets.earthly.webapp.controller.form.UserSearchForm;
import jp.co.project.planets.earthly.webapp.exception.ForbiddenException;
import jp.co.project.planets.earthly.webapp.model.dto.UserSearchDto;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.service.UserService;
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

import static jp.co.project.planets.earthly.webapp.constant.ModelKey.*;

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
     *         ユーザー検索リクエスト
     * @param pageable
     *         ページャー
     * @param userInfoDto
     *         ユーザー情報
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
     *         ユーザーID
     * @param userInfoDto
     *         ユーザー情報
     * @return ユーザー詳細画面
     */
    @GetMapping("{userId}")
    public ModelAndView get(@PathVariable("userId") final String id,
            @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var userEntity = userService.getById(id, userInfoDto);
        final var modelAndView = new ModelAndView("users/detail");
        modelAndView.addObject(userEntity);
        return modelAndView;
    }

    /**
     * ユーザー登録
     *
     * @param model
     *         model
     * @param userInfoDto
     *         ユーザー情報
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
     *         ユーザー登録フォーム
     * @param bindingResult
     *         binding result
     * @param redirectAttributes
     *         redirect attributes
     * @param model
     *         model
     * @param userInfoDto
     *         ユーザー情報
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
            model.asMap().forEach(redirectAttributes::addFlashAttribute);
            redirectAttributes.addFlashAttribute(ERROR_CODE, e.getErrorCode());
            redirectAttributes.addFlashAttribute(MESSAGE_ARGS, e.getMessageKeyArgs());
            return new ModelAndView("redirect:/users/entry");
        }
    }

    /**
     * ユーザー登録
     *
     * @param userEntryForm
     *         ユーザー登録フォーム
     * @param bindingResult
     *         binding result
     * @param redirectAttributes
     *         redirect attributes
     * @param model
     *         model
     * @param userInfoDto
     *         ユーザー情報
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
}
