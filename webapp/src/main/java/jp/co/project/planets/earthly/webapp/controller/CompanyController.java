package jp.co.project.planets.earthly.webapp.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

}
