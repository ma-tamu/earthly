package jp.co.project.planets.earthly.webapp.controller;

import static jp.co.project.planets.earthly.webapp.constant.ModelKey.*;
import static jp.co.project.planets.earthly.webapp.constant.ViewName.*;

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

import jp.co.project.planets.earthly.webapp.constant.MessageKey;
import jp.co.project.planets.earthly.webapp.controller.form.company.CompanyEditForm;
import jp.co.project.planets.earthly.webapp.controller.form.company.CompanyGroupEntryForm;
import jp.co.project.planets.earthly.webapp.controller.form.company.CompanyGroupSearchForm;
import jp.co.project.planets.earthly.webapp.controller.form.company.CompanyManagementUserAssignForm;
import jp.co.project.planets.earthly.webapp.controller.form.company.CompanyManagementUserSearchForm;
import jp.co.project.planets.earthly.webapp.controller.form.company.CompanyManagementUserUnassignForm;
import jp.co.project.planets.earthly.webapp.controller.form.company.CompanyNotAssignUserSearchForm;
import jp.co.project.planets.earthly.webapp.emuns.ErrorCode;
import jp.co.project.planets.earthly.webapp.exception.BadRequestException;
import jp.co.project.planets.earthly.webapp.exception.ForbiddenException;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.service.CompanyGroupService;
import jp.co.project.planets.earthly.webapp.service.CompanyService;

/**
 * 会社詳細
 */
@Controller
@RequestMapping("companies/{id}")
public class CompanyDetailController {

    private final CompanyService companyService;
    private final CompanyGroupService companyGroupService;

    public CompanyDetailController(final CompanyService companyService, final CompanyGroupService companyGroupService) {
        this.companyService = companyService;
        this.companyGroupService = companyGroupService;
    }

    /**
     * 会社詳細
     *
     * @param id
     *            会社ID
     * @param model
     *            model
     * @param userInfoDto
     *            ユーザー情報
     * @return 会社詳細
     */
    @GetMapping
    public ModelAndView index(@PathVariable("id") final String id, final Model model,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var companyDetailDto = companyService.detail(id, userInfoDto);
        final var company = companyDetailDto.company();
        final var companyEditForm = new CompanyEditForm(company.getName(), company.getCountryId());
        final var companyManagementUserSearchForm = new CompanyManagementUserSearchForm(null, null, null, false);
        final var companyGroupEntryForm = new CompanyGroupEntryForm(null);
        return new ModelAndView("companies/detail")
                .addObject(company)
                .addObject("countryList", companyDetailDto.countryList())
                .addObject(companyEditForm)
                .addObject(companyManagementUserSearchForm)
                .addObject(companyGroupEntryForm)
                .addObject("groupPage", companyDetailDto.groupPage())
                .addObject("managementUserPage", companyDetailDto.managementUserPage())
                .addObject("unassignedManagementUserPage", companyDetailDto.notManagementUserPage())
                .addAllObjects(model.asMap());
    }

    /**
     * 会社編集
     * 
     * @param id
     *            会社ID
     * @param companyEditForm
     *            会社編集FORM
     * @param bindingResult
     *            binding result
     * @param redirectAttributes
     *            redirect attributes
     * @param model
     *            model
     * @param userInfoDto
     *            ユーザー情報
     * @return 編集結果
     */
    @PostMapping("edit")
    public ModelAndView edit(@PathVariable("id") final String id,
        @ModelAttribute @Validated final CompanyEditForm companyEditForm,
        final BindingResult bindingResult, final RedirectAttributes redirectAttributes, final Model model,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        final var modelAndView = new ModelAndView("redirect:/companies/%s".formatted(id));
        model.asMap().forEach(redirectAttributes::addFlashAttribute);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(EDIT_MODAL, true);
            return modelAndView;
        }
        try {
            companyService.validateEdit(id, companyEditForm.toDto(), userInfoDto);
            redirectAttributes.addFlashAttribute(READ_ONLY, true);
        } catch (final BadRequestException | ForbiddenException e) {
            model.addAttribute(MESSAGE, e.getErrorCode().getMessageKey());
            model.addAttribute(MESSAGE_ARGS, e.getMessageKeyArgs());
        }

        return modelAndView;
    }

    /**
     * 会社更新
     * 
     * @param id
     *            会社ID
     * @param companyEditForm
     *            会社編集FORM
     * @param bindingResult
     *            binding result
     * @param redirectAttributes
     *            redirect attributes
     * @param model
     *            model
     * @param userInfoDto
     *            ユーザー情報
     * @return 更新結果
     */
    @PostMapping("update")
    public ModelAndView update(@PathVariable("id") final String id,
        @ModelAttribute @Validated final CompanyEditForm companyEditForm,
        final BindingResult bindingResult, final RedirectAttributes redirectAttributes, final Model model,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var modelAndView = new ModelAndView("redirect:/companies/%s".formatted(id));
        model.asMap().forEach(redirectAttributes::addFlashAttribute);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(EDIT_MODAL, true);
            return modelAndView;
        }

        try {
            final var message = companyService.update(id, companyEditForm.toDto(), userInfoDto);
            redirectAttributes.addFlashAttribute(SUCCESS, message);
        } catch (final BadRequestException | ForbiddenException e) {
            model.addAttribute(MESSAGE, e.getErrorCode().getMessageKey());
            model.addAttribute(MESSAGE_ARGS, e.getMessageKeyArgs());
        }

        return modelAndView;
    }

    /**
     * 会社削除
     * 
     * @param id
     *            会社ID
     * @param redirectAttributes
     *            redirect attributes
     * @param model
     *            model
     * @param userInfoDto
     *            ユーザー情報
     * @return 削除結果
     */
    @PostMapping("delete")
    public ModelAndView delete(@PathVariable("id") final String id, final RedirectAttributes redirectAttributes,
        final Model model, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        try {
            companyService.delete(id, userInfoDto);
            redirectAttributes.addFlashAttribute(SUCCESS, MessageKey.DELETE_SUCCESS);
        } catch (final BadRequestException | ForbiddenException e) {
            model.asMap().forEach(redirectAttributes::addFlashAttribute);
            return new ModelAndView("redirect:/companies/%s".formatted(id));
        }

        return new ModelAndView(REDIRECT_COMPANY_LIST);
    }

    @GetMapping("management-users")
    public ModelAndView searchManagementUser(@PathVariable("id") final String id,
        final CompanyManagementUserSearchForm companyManagementUserSearchForm, @PageableDefault final Pageable pageable,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var managementUserPage = companyService.searchManagementUser(id, companyManagementUserSearchForm.toDto(),
                pageable, userInfoDto);
        return new ModelAndView("companies/detail::managementUserContent")//
                .addObject("managementUserPage", managementUserPage);
    }

    @GetMapping("management-users/not-assigns")
    public ModelAndView searchNotAssignManagementUser(@PathVariable("id") final String id,
        final CompanyNotAssignUserSearchForm companyNotAssignUserSearchForm, @PageableDefault final Pageable pageable,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var unassignedManagementUserPage = companyService.searchNotAssignUserUser(id,
                companyNotAssignUserSearchForm.toDto(), pageable, userInfoDto);
        return new ModelAndView("companies/modal::unassignedManagementUserPage") //
                .addObject("unassignedManagementUserPage", unassignedManagementUserPage);
    }

    @PostMapping("management-users/assigns")
    public ModelAndView assignManagementUser(@PathVariable("id") final String id,
        @ModelAttribute @Validated final CompanyManagementUserAssignForm companyManagementUserAssignForm,
        final BindingResult bindingResult, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView(TOAST_DANGER).addObject(MESSAGE, ErrorCode.EWA5XX999.getMessageKey());
        }

        try {
            companyService.assignManagementUser(id, companyManagementUserAssignForm.userId(), userInfoDto);
            return new ModelAndView(TOAST_SUCCESS).addObject(MESSAGE, MessageKey.UNASSIGN_SUCCESS);
        } catch (final BadRequestException e) {
            return new ModelAndView(TOAST_DANGER).addObject(MESSAGE, e.getErrorCode().getMessageKey())
                    .addObject(MESSAGE_ARGS, e.getMessageKeyArgs());
        }
    }

    @PostMapping("management-users/unassigns")
    public ModelAndView unassignManagementUser(@PathVariable("id") final String id,
        @ModelAttribute @Validated final CompanyManagementUserUnassignForm companyManagementUserUnassignForm,
        final BindingResult bindingResult, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView(TOAST_DANGER).addObject(MESSAGE, ErrorCode.EWA5XX999.getMessageKey());
        }

        try {
            companyService.unassignManagementUser(id, companyManagementUserUnassignForm.userId(), userInfoDto);
            return new ModelAndView(TOAST_SUCCESS).addObject(MESSAGE, MessageKey.UNASSIGN_SUCCESS);
        } catch (final BadRequestException e) {
            return new ModelAndView(TOAST_DANGER).addObject(MESSAGE, e.getErrorCode().getMessageKey())
                    .addObject(MESSAGE_ARGS, e.getMessageKeyArgs());
        }
    }

    @GetMapping("groups")
    public ModelAndView searchGroup(@PathVariable("id") final String id,
        final CompanyGroupSearchForm companyGroupSearchForm, @PageableDefault final Pageable pageable,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var groupPage = companyService.searchGroup(id, companyGroupSearchForm.groupName(), pageable, userInfoDto);
        return new ModelAndView("companies/detail::groupPage", "groupPage", groupPage);
    }

    @PostMapping("groups/entries")
    public ModelAndView entryGroup(@PathVariable("id") final String id,
        @ModelAttribute @Validated final CompanyGroupEntryForm companyGroupEntryForm, final BindingResult bindingResult,
        final RedirectAttributes redirectAttributes, final Model model,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        model.asMap().forEach(redirectAttributes::addFlashAttribute);
        if (bindingResult.hasErrors()) {
            return new ModelAndView("redirect:/companies/%s".formatted(id));
        }
        final var groupId = companyGroupService.entry(id, companyGroupEntryForm.groupName(), userInfoDto);
        return new ModelAndView("redirect:/companies/%s/groups/%s".formatted(id, groupId));
    }

}
