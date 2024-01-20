package jp.co.project.planets.earthly.webapp.controller;

import java.util.Collections;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.project.planets.earthly.schema.db.entity.Company;
import jp.co.project.planets.earthly.schema.db.entity.Organization;
import jp.co.project.planets.earthly.schema.db.entity.User;
import jp.co.project.planets.earthly.webapp.controller.form.company.CompanyEditForm;
import jp.co.project.planets.earthly.webapp.controller.form.company.CompanySearchForm;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;

/**
 * 会社コントローラー
 */
@Controller
@RequestMapping("companies")
public class CompanyController {

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
        final var modelAndView = new ModelAndView("companies/index");
        modelAndView.addObject(new PageImpl<Company>(Collections.emptyList()));
        return modelAndView;
    }

    @GetMapping("{id}")
    public ModelAndView detail(@PathVariable("id") final String id, final Model model,
            @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var company = new Company("d9f942e88b1411ed90190242ac120003", "GUND-ARM Inc.", null, null, null, null,
                null,
                false);
        return new ModelAndView("companies/detail")
                .addObject("company", company)
                .addObject("companyEditForm", new CompanyEditForm("GUND-ARM Inc."))
                .addObject("groupPage", new PageImpl<Organization>(Collections.emptyList()))
                .addObject("managementUserPage", new PageImpl<User>(Collections.emptyList()));
    }

    @GetMapping("entry")
    public ModelAndView entry(final Model model, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        return new ModelAndView("companies/entry");
    }
}
