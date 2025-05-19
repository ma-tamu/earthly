package jp.co.project.planets.earthly.webapp.controller;

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

import jp.co.project.planets.earthly.webapp.constant.ModelKey;
import jp.co.project.planets.earthly.webapp.controller.form.company.CompanyEntryForm;
import jp.co.project.planets.earthly.webapp.controller.form.company.CompanyEntryFormBuilder;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.service.CompanyService;
import jp.co.project.planets.earthly.webapp.service.CountryService;

/**
 * Controller responsible for handling company entry-related operations.
 */
@Controller
@RequestMapping("companies")
public class CompanyEntryController {

    private final CompanyService companyService;
    private final CountryService countryService;

    public CompanyEntryController(final CompanyService companyService, final CountryService countryService) {
        this.companyService = companyService;
        this.countryService = countryService;
    }

    /**
     * 会社登録
     *
     * @param model
     *            model
     * @param userInfoDto
     *            ユーザー情報
     * @return 会社登録
     */
    @GetMapping("entries")
    public ModelAndView index(final Model model, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        companyService.validateEntryPermission(userInfoDto.account());
        final var countryList = countryService.findAll();
        return new ModelAndView("companies/entry")
                .addObject("countryList", countryList)
                .addObject(CompanyEntryFormBuilder.builder().build())
                .addAllObjects(model.asMap());
    }

    @PostMapping("entries")
    public ModelAndView entry(@ModelAttribute @Validated final CompanyEntryForm companyEntryForm,
        final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        final var modelAndView = new ModelAndView(REDIRECT_COMPANY_ENTRY);
        model.asMap().forEach(redirectAttributes::addFlashAttribute);

        if (bindingResult.hasErrors()) {
            return modelAndView;
        }

        companyService.validateEntryPermission(userInfoDto.account());
        redirectAttributes.addFlashAttribute(ModelKey.READ_ONLY, true);
        return modelAndView;
    }

    @PostMapping("create")
    public ModelAndView create(@ModelAttribute @Validated final CompanyEntryForm companyEntryForm,
        final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        model.asMap().forEach(redirectAttributes::addFlashAttribute);

        if (bindingResult.hasErrors()) {
            return new ModelAndView(REDIRECT_COMPANY_ENTRY);
        }

        final var id = companyService.create(companyEntryForm.toDto(), userInfoDto.account());

        return new ModelAndView("redirect:/companies/%s".formatted(id));
    }

}
