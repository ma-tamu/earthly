package jp.co.project.planets.earthly.webapp.controller;

import static jp.co.project.planets.earthly.webapp.constant.ModelKey.*;
import static jp.co.project.planets.earthly.webapp.constant.ViewName.*;

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

import jp.co.project.planets.earthly.webapp.controller.form.user.UserEntryForm;
import jp.co.project.planets.earthly.webapp.exception.ForbiddenException;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.service.UserService;

@Controller
@RequestMapping("users")
public class UserEntryController {

    private final UserService userService;

    public UserEntryController(final UserService userService) {
        this.userService = userService;
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
    @GetMapping("entries")
    public ModelAndView index(final Model model, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        userService.validateUserAddOperationPermission(userInfoDto.account());

        final var modelAndView = new ModelAndView("users/entry");
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
    @PostMapping("entries")
    public ModelAndView entry(@ModelAttribute @Validated final UserEntryForm userEntryForm,
        final BindingResult bindingResult, final RedirectAttributes redirectAttributes, final Model model,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var modelAndView = new ModelAndView("redirect:/users/entries");
        model.asMap().forEach(redirectAttributes::addFlashAttribute);
        if (bindingResult.hasErrors()) {
            return modelAndView;
        }
        try {
            userService.validateEntryOperation(userEntryForm.toDto(), userInfoDto.account());
            redirectAttributes.addFlashAttribute(READ_ONLY, true);
            return modelAndView;
        } catch (final ForbiddenException e) {
            redirectAttributes.addFlashAttribute(ERROR_CODE, e.getErrorCode());
            redirectAttributes.addFlashAttribute(MESSAGE_ARGS, e.getMessageKeyArgs());
            return new ModelAndView("redirect:/users/entries");
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
            final var userId = userService.create(userEntryForm.toDto(), userInfoDto.account());
            return new ModelAndView(REDIRECT_USER_DETAIL.formatted(userId));
        } catch (final ForbiddenException e) {
            model.asMap().forEach(redirectAttributes::addFlashAttribute);
            redirectAttributes.addFlashAttribute(ERROR_CODE, e.getErrorCode());
            redirectAttributes.addFlashAttribute(MESSAGE_ARGS, e.getMessageKeyArgs());
            return new ModelAndView("redirect:/users/entry");
        }
    }
}
