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
import jp.co.project.planets.earthly.webapp.controller.form.company.GroupBelongForm;
import jp.co.project.planets.earthly.webapp.controller.form.company.GroupBelongUserSearchForm;
import jp.co.project.planets.earthly.webapp.controller.form.company.GroupEditForm;
import jp.co.project.planets.earthly.webapp.controller.form.company.GroupNotBelongForm;
import jp.co.project.planets.earthly.webapp.controller.form.company.GroupNotBelongUserSearchForm;
import jp.co.project.planets.earthly.webapp.emuns.ErrorCode;
import jp.co.project.planets.earthly.webapp.exception.BadRequestException;
import jp.co.project.planets.earthly.webapp.exception.ForbiddenException;
import jp.co.project.planets.earthly.webapp.exception.NotFoundException;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.service.CompanyGroupService;

@Controller
@RequestMapping("companies/{companyId}/groups/{id}")
public class CompanyGroupDetailController {

    private final CompanyGroupService companyGroupService;

    public CompanyGroupDetailController(final CompanyGroupService companyGroupService) {
        this.companyGroupService = companyGroupService;
    }

    @GetMapping
    public ModelAndView index(@PathVariable("companyId") final String companyId, @PathVariable("id") final String id,
        final Model model, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var detail = companyGroupService.detail(id, companyId, userInfoDto.account());
        final var groupEditForm = new GroupEditForm(detail.organization().getName());
        final var groupBelongUserSearchForm = new GroupBelongUserSearchForm(null, null, false);
        return new ModelAndView("companies/groups/detail") //
                .addObject(groupEditForm).addObject(groupBelongUserSearchForm)
                .addObject("group", detail.organization()) //
                .addObject("belongUserPage", detail.belongUserPage()) //
                .addObject("notBelongUserPage", detail.notBelongUserPage()) //
                .addAllObjects(model.asMap());
    }

    @PostMapping("edit")
    public ModelAndView edit(@PathVariable("companyId") final String companyId, @PathVariable("id") final String id,
        @ModelAttribute @Validated final GroupEditForm groupEditForm, final BindingResult bindingResult,
        final RedirectAttributes redirectAttributes, final Model model,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        final var modelAndView = new ModelAndView("redirect:/companies/%s/groups/%s".formatted(companyId, id));
        model.asMap().forEach(redirectAttributes::addFlashAttribute);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(EDIT_MODAL, true);
            return modelAndView;
        }
        redirectAttributes.addFlashAttribute(READ_ONLY, true);
        return modelAndView;
    }

    @PostMapping("update")
    public ModelAndView update(@PathVariable("companyId") final String companyId, @PathVariable("id") final String id,
        @ModelAttribute @Validated final GroupEditForm groupEditForm, final BindingResult bindingResult,
        final RedirectAttributes redirectAttributes, final Model model,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var modelAndView = new ModelAndView("redirect:/companies/%s/groups/%s".formatted(companyId, id));
        model.asMap().forEach(redirectAttributes::addFlashAttribute);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(EDIT_MODAL, true);
            return modelAndView;
        }

        try {
            final var message = companyGroupService.update(id, companyId, groupEditForm.name(), userInfoDto.account());
            redirectAttributes.addFlashAttribute(SUCCESS, message);
        } catch (final ForbiddenException | NotFoundException e) {
            redirectAttributes.addFlashAttribute(MESSAGE, e.getErrorCode().getMessageKey())
                    .addFlashAttribute(MESSAGE_ARGS, e.getMessageKeyArgs()).addFlashAttribute(EDIT_MODAL, true);
        }
        return modelAndView;
    }

    @PostMapping("delete")
    public ModelAndView delete(@PathVariable("companyId") final String companyId, @PathVariable("id") final String id,
        final RedirectAttributes redirectAttributes, final Model model,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        try {
            companyGroupService.delete(id, companyId, userInfoDto.account());
            redirectAttributes.addFlashAttribute(SUCCESS, MessageKey.DELETE_SUCCESS);
            return new ModelAndView("redirect:/companies/%s".formatted(companyId));
        } catch (final BadRequestException | ForbiddenException e) {
            model.asMap().forEach(redirectAttributes::addFlashAttribute);
            return new ModelAndView("redirect:/companies/%s/groups/%s".formatted(companyId, id));
        }
    }

    @GetMapping("users")
    public ModelAndView searchBelongUser(@PathVariable("companyId") final String companyId,
        @PathVariable("id") final String id, final GroupBelongUserSearchForm groupBelongUserSearchForm,
        @PageableDefault final Pageable pageable, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var belongUserPage = companyGroupService.searchBelongUser(id, companyId,
                groupBelongUserSearchForm.toDto(), pageable, userInfoDto.account());
        return new ModelAndView("companies/groups/detail::belongUserContent", "belongUserPage", belongUserPage);
    }

    @GetMapping("users/not-belongs")
    public ModelAndView searchNotBelongUser(@PathVariable("companyId") final String companyId,
        @PathVariable("id") final String id, final GroupNotBelongUserSearchForm groupNotBelongUserSearchForm,
        @PageableDefault final Pageable pageable, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        final var notBelongUserPage = companyGroupService.searchNotBelongUser(id, companyId,
                groupNotBelongUserSearchForm.toDto(), pageable, userInfoDto.account());

        return new ModelAndView("companies/groups/modal::notBelongUserPage", "notBelongUserPage", notBelongUserPage);
    }

    @PostMapping("users/belongs")
    public ModelAndView assign(@PathVariable("companyId") final String companyId, @PathVariable("id") final String id,
        @ModelAttribute @Validated final GroupBelongForm groupBelongForm, final BindingResult bindingResult,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView(TOAST_DANGER).addObject(MESSAGE, ErrorCode.EWA5XX999.getMessageKey());
        }

        try {
            companyGroupService.assign(id, companyId, groupBelongForm.userId(), userInfoDto.account());
            return new ModelAndView(TOAST_SUCCESS).addObject(MESSAGE, MessageKey.ASSIGN_SUCCESS);
        } catch (final BadRequestException e) {
            return new ModelAndView(TOAST_DANGER).addObject(MESSAGE, e.getErrorCode().getMessageKey())
                    .addObject(MESSAGE_ARGS, e.getMessageKeyArgs());
        }
    }

    @PostMapping("users/not-belong")
    public ModelAndView unassign(@PathVariable("companyId") final String companyId, @PathVariable("id") final String id,
        @ModelAttribute @Validated final GroupNotBelongForm groupNotBelongForm, final BindingResult bindingResult,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView(TOAST_DANGER).addObject(MESSAGE, ErrorCode.EWA5XX999.getMessageKey());
        }

        try {
            companyGroupService.unassign(id, companyId, groupNotBelongForm.userId(), userInfoDto.account());
            return new ModelAndView(TOAST_SUCCESS).addObject(MESSAGE, MessageKey.UNASSIGN_SUCCESS);
        } catch (final BadRequestException e) {
            return new ModelAndView(TOAST_DANGER).addObject(MESSAGE, e.getErrorCode().getMessageKey())
                    .addObject(MESSAGE_ARGS, e.getMessageKeyArgs());
        }
    }
}
