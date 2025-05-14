package jp.co.project.planets.earthly.webapp.controller;

import static jp.co.project.planets.earthly.webapp.constant.ModelKey.*;

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
import jp.co.project.planets.earthly.webapp.controller.form.company.GroupEditForm;
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
        final var detail = companyGroupService.detail(id, companyId, userInfoDto);
        final var groupEditForm = new GroupEditForm(detail.organization().getName());
        return new ModelAndView("companies/groups/detail") //
                .addObject(groupEditForm)
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
            final var message = companyGroupService.update(id, companyId, groupEditForm.name(), userInfoDto);
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
            companyGroupService.delete(id, companyId, userInfoDto);
            redirectAttributes.addFlashAttribute(SUCCESS, MessageKey.DELETE_SUCCESS);
            return new ModelAndView("redirect:/companies/%s".formatted(companyId));
        } catch (final BadRequestException | ForbiddenException e) {
            model.asMap().forEach(redirectAttributes::addFlashAttribute);
            return new ModelAndView("redirect:/companies/%s/groups/%s".formatted(companyId, id));
        }
    }

}
