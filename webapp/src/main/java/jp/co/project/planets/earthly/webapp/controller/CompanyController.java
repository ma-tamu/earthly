package jp.co.project.planets.earthly.webapp.controller;

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

import jp.co.project.planets.earthly.schema.db.entity.Company;
import jp.co.project.planets.earthly.schema.db.entity.Organization;
import jp.co.project.planets.earthly.schema.db.entity.User;
import jp.co.project.planets.earthly.webapp.constant.ModelKey;
import jp.co.project.planets.earthly.webapp.controller.form.company.CompanyEditForm;
import jp.co.project.planets.earthly.webapp.controller.form.company.CompanyEntryForm;
import jp.co.project.planets.earthly.webapp.controller.form.company.CompanyEntryFormBuilder;
import jp.co.project.planets.earthly.webapp.controller.form.company.CompanySearchForm;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.service.CompanyService;
import jp.co.project.planets.earthly.webapp.service.CountryService;

/**
 * 会社コントローラー
 */
@Controller
@RequestMapping("companies")
public class CompanyController {

    private final CompanyService companyService;
    private final CountryService countryService;

    public CompanyController(final CompanyService companyService, final CountryService countryService) {
        this.companyService = companyService;
        this.countryService = countryService;
    }

    /**
     * 会社検索
     * 
     * @param companySearchForm
     *            会社検索FORM
     * @param pageable
     *            ページャー
     * @param userInfoDto
     *            ユーザー情報
     * @return 会社一覧
     */
    @GetMapping
    public ModelAndView search(@ModelAttribute final CompanySearchForm companySearchForm,
        @PageableDefault final Pageable pageable, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var companyEntityPage = companyService.search(companySearchForm.name(), pageable, userInfoDto);
        return new ModelAndView("companies/index").addObject(companyEntityPage);
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
    @GetMapping("{id}")
    public ModelAndView detail(@PathVariable("id") final String id, final Model model,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var company = new Company("d9f942e88b1411ed90190242ac120003", "GUND-ARM Inc.", null, null, null, null,
                null,
                false);
        return new ModelAndView("companies/detail")
                .addObject("company", company)
                .addObject("companyEditForm", new CompanyEditForm("GUND-ARM Inc.", ""))
                .addObject("groupPage", new PageImpl<Organization>(Collections.emptyList()))
                .addObject("managementUserPage", new PageImpl<User>(Collections.emptyList()))
                .addObject("unassignedManagementUserPage", new PageImpl<User>(Collections.emptyList()));
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
    public ModelAndView entry(final Model model, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        companyService.entry(userInfoDto);
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
        model.asMap().forEach(redirectAttributes::addFlashAttribute);
        redirectAttributes.addFlashAttribute(ModelKey.READ_ONLY, true);
        return new ModelAndView("redirect:/companies/entries");
    }

    @PostMapping("create")
    public ModelAndView create(@ModelAttribute @Validated final CompanyEntryForm companyEntryForm,
        final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        // if (bindingResult.hasErrors()) {
        // return new ModelAndView("redirect:/companies/entry");
        // }
        return new ModelAndView("redirect:/companies/%s".formatted("6fcc6579e42b58845ea7cbdd0752d861"));
    }
}
