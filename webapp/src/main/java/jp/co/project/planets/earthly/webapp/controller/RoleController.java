package jp.co.project.planets.earthly.webapp.controller;

import java.util.Collections;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.project.planets.earthly.schema.db.entity.Company;
import jp.co.project.planets.earthly.webapp.controller.form.role.RoleSearchForm;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;

/**
 * ロールコントローラー
 */
@Controller
@RequestMapping("roles")
public class RoleController {

    @GetMapping
    public ModelAndView search(@ModelAttribute final RoleSearchForm roleSearchForm,
            @PageableDefault final Pageable pageable, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        return new ModelAndView("roles/index").addObject(new PageImpl<Company>(Collections.emptyList()));
    }
}
