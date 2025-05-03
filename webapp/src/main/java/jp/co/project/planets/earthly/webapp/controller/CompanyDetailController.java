package jp.co.project.planets.earthly.webapp.controller;

import static jp.co.project.planets.earthly.webapp.constant.ModelKey.*;
import static jp.co.project.planets.earthly.webapp.constant.ViewName.*;

import java.util.Collections;

import org.springframework.data.domain.PageImpl;
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

import jakarta.servlet.ServletRequest;
import jp.co.project.planets.earthly.schema.db.entity.Organization;
import jp.co.project.planets.earthly.schema.db.entity.User;
import jp.co.project.planets.earthly.webapp.constant.MessageKey;
import jp.co.project.planets.earthly.webapp.controller.form.company.CompanyEditForm;
import jp.co.project.planets.earthly.webapp.exception.BadRequestException;
import jp.co.project.planets.earthly.webapp.exception.ForbiddenException;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.service.CompanyService;

/**
 * 会社詳細
 */
@Controller
@RequestMapping("companies/{id}")
public class CompanyDetailController {

    private final CompanyService companyService;

    public CompanyDetailController(final CompanyService companyService) {
        this.companyService = companyService;
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
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto, final ServletRequest servletRequest) {
        final var companyDetailDto = companyService.findDetail(id, userInfoDto);
        final var company = companyDetailDto.company();
        final var companyEditForm = new CompanyEditForm(company.getName(), company.getCountryId());
        return new ModelAndView("companies/detail")
                .addObject(company)
                .addObject("countryList", companyDetailDto.countryList())
                .addObject(companyEditForm)
                .addObject("groupPage", new PageImpl<Organization>(Collections.emptyList()))
                .addObject("managementUserPage", companyDetailDto.managementUserPage())
                .addObject("unassignedManagementUserPage", new PageImpl<User>(Collections.emptyList()))
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
}
