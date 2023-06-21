package jp.co.project.planets.earthly.webapp.controller;

import static jp.co.project.planets.earthly.webapp.constant.MessageKey.*;
import static jp.co.project.planets.earthly.webapp.constant.ModelKey.*;
import static jp.co.project.planets.earthly.webapp.constant.ViewName.*;
import static jp.co.project.planets.earthly.webapp.emuns.ErrorMessageKey.*;

import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.project.planets.earthly.webapp.constant.ViewName;
import jp.co.project.planets.earthly.webapp.controller.form.user.PasswordEditForm;
import jp.co.project.planets.earthly.webapp.controller.form.user.UserAssignRoleForm;
import jp.co.project.planets.earthly.webapp.controller.form.user.UserEntryForm;
import jp.co.project.planets.earthly.webapp.controller.form.user.UserSearchForm;
import jp.co.project.planets.earthly.webapp.controller.form.user.UserUnassignedRoleForm;
import jp.co.project.planets.earthly.webapp.controller.form.user.UserUnassignedRoleSearchForm;
import jp.co.project.planets.earthly.webapp.controller.form.user.UserUpdateForm;
import jp.co.project.planets.earthly.webapp.exception.BadRequestException;
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
        final var userDetailDto = userService.getById(id, userInfoDto);
        final var modelAndView = new ModelAndView("users/detail");
        final var userEntity = userDetailDto.userEntity();
        final var userUpdateForm = new UserUpdateForm(userEntity.name(), userEntity.mail(), userEntity.language(),
                userEntity.timezone(), userEntity.company().name(), userEntity.company().id(), userEntity.lockout(),
                userEntity.is2fa());
        modelAndView.addObject(userUpdateForm);
        modelAndView.addObject(userDetailDto);
        modelAndView.addObject(ROLE_PAGE, new PageImpl<>(userEntity.roleList()));
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
            return new ModelAndView(REDIRECT_USER_DETAIL.formatted(userId));
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
    @PostMapping("{userId}/delete")
    public ModelAndView delete(@PathVariable("userId") final String id, final RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        try {
            final var message = userService.delete(id, userInfoDto);
            redirectAttributes.addFlashAttribute(SUCCESS, message);
            return new ModelAndView("redirect:/users");
        } catch (final BadRequestException | ForbiddenException e) {
            return new ModelAndView("redirect:/users/%s".formatted(id));
        }
    }

    /**
     * 割り当て済みロールを検索
     * 
     * @param id
     *            ユーザーID
     * @param roleNameOptional
     *            検索キーワード（ロール名）
     * @param pageable
     *            ページャー
     * @param userInfoDto
     *            ユーザー情報
     * @return 検索結果
     */
    @GetMapping("{userId}/roles/assigns")
    public ModelAndView searchAssignedRole(@PathVariable("userId") final String id,
            @RequestParam("roleName") final Optional<String> roleNameOptional,
            @PageableDefault final Pageable pageable,
            @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var rolePage = userService.findAssignedRole(id, roleNameOptional, pageable, userInfoDto);
        return new ModelAndView("users/detail::roleContent", ROLE_PAGE, rolePage);
    }

    /**
     * 対象ユーザーの未割当ロールを検索
     * 
     * @param id
     *            ユーザーID
     * @param form
     *            検索フォーム
     * @param pageable
     *            ページャー
     * @param userInfoDto
     *            ユーザー情報
     * @return 検索結果
     */
    @GetMapping("{userId}/roles")
    public ModelAndView searchUnassignedRole(@PathVariable("userId") final String id,
            final UserUnassignedRoleSearchForm form, @PageableDefault final Pageable pageable,
            @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var rolePage = userService.findUnassignedRole(id, form.roleName(), pageable, userInfoDto);
        final var modelAndView = new ModelAndView("users/assign::searchResult");
        modelAndView.addObject("rolePage", rolePage);
        return modelAndView;
    }

    /**
     * ロール割り当て
     * 
     * @param id
     *            ユーザーID
     * @param form
     *            ロール割り当てFROM
     * @param bindingResult
     *            binding result
     * @param userInfoDto
     *            ユーザー情報
     * @return トースト
     */
    @PostMapping("{userId}/roles/assigns")
    public ModelAndView assignRole(@PathVariable("userId") final String id, @Validated final UserAssignRoleForm form,
            final BindingResult bindingResult, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView(ViewName.ALERT_DANGER, MESSAGE, NOT_SELECTION_ASSIGN_ROLE);
        }

        try {
            userService.assignRole(id, form.assign(), userInfoDto);
            return new ModelAndView(ViewName.ALERT_SUCCESS, MESSAGE, ASSIGN_SUCCESS);
        } catch (final ForbiddenException e) {
            return new ModelAndView(ViewName.ALERT_DANGER, MESSAGE, e.getErrorCode().getMessageKey());
        }
    }

    /**
     * ロール解除
     * 
     * @param id
     *            ユーザーID
     * @param form
     *            ロール解除FORM
     * @param bindingResult
     *            binding result
     * @param userInfoDto
     *            ユーザー情報
     * @return トースト
     */
    @PostMapping("{userId}/roles/unassigns")
    public ModelAndView unassigns(@PathVariable("userId") final String id, final UserUnassignedRoleForm form,
            final BindingResult bindingResult, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView(ViewName.ALERT_DANGER, MESSAGE, NOT_SELECTION_ASSIGN_ROLE);
        }
        try {
            userService.unassignedRole(id, form.unassigns(), userInfoDto);
            return new ModelAndView(ViewName.ALERT_SUCCESS, MESSAGE, ASSIGN_SUCCESS);
        } catch (final ForbiddenException e) {
            return new ModelAndView(ViewName.ALERT_DANGER, MESSAGE, e.getErrorCode().getMessageKey());
        }
    }

    /**
     * パスワード編集
     * 
     * @param id
     *            ユーザーID
     * @param passwordEditForm
     *            パスワード編集FROM
     * @param userInfoDto
     *            ユーザー情報
     * @return ユーザー詳細
     */
    @PostMapping("{userId}/password/edit")
    public ModelAndView editPassword(@PathVariable("userId") final String id, final PasswordEditForm passwordEditForm,
            final RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        try {
            userService.editPassword(id, passwordEditForm.currentPassword(), passwordEditForm.newPassword(),
                    passwordEditForm.confirmNewPassword(), userInfoDto);
            redirectAttributes.addFlashAttribute(MESSAGE, UPDATE_PASSWORD_SUCCESS);
        } catch (final BadRequestException | ForbiddenException e) {
            redirectAttributes.addFlashAttribute(ERROR_CODE, e.getErrorCode());
            redirectAttributes.addFlashAttribute(MESSAGE, e.getErrorCode().getMessageKey());
        }
        return new ModelAndView(REDIRECT_USER_DETAIL.formatted(id));
    }
}
