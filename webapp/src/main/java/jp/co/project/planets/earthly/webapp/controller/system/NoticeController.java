package jp.co.project.planets.earthly.webapp.controller.system;

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
import jp.co.project.planets.earthly.webapp.controller.form.system.notice.NoticeSearchForm;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;

/**
 * お知らせコントローラー
 */
@Controller
@RequestMapping("systems/notices")
public class NoticeController {

    @GetMapping
    public ModelAndView search(@ModelAttribute final NoticeSearchForm noticeSearchForm,
            @PageableDefault final Pageable pageable, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        return new ModelAndView("systems/notices/index").addObject(new PageImpl<Company>(Collections.emptyList()));
    }

}
