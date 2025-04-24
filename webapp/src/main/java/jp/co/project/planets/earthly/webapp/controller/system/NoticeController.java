package jp.co.project.planets.earthly.webapp.controller.system;

import java.util.Collections;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.project.planets.earthly.schema.db.entity.Company;
import jp.co.project.planets.earthly.webapp.constant.ModelKey;
import jp.co.project.planets.earthly.webapp.controller.form.system.notice.NoticeEntryForm;
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

    @GetMapping("entry")
    public ModelAndView entry(final Model model, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        return new ModelAndView("systems/notices/entry").addObject(new NoticeEntryForm("", "", null, null, false))
                .addAllObjects(model.asMap());
    }

    @PostMapping("entry")
    public ModelAndView entry(@ModelAttribute @Validated final NoticeEntryForm noticeEntryForm,
        final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        redirectAttributes.addFlashAttribute(ModelKey.READ_ONLY, true);
        return new ModelAndView("redirect:/notices/entry");
    }

    @PostMapping("create")
    public ModelAndView create(@ModelAttribute @Validated final NoticeEntryForm noticeEntryForm,
        final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        return new ModelAndView("redirect:/notices/" + UUID.randomUUID().toString());
    }

}
