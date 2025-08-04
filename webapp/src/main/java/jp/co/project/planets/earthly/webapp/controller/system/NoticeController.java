package jp.co.project.planets.earthly.webapp.controller.system;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.project.planets.earthly.webapp.controller.form.system.notice.NoticeSearchForm;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.service.NoticeService;

/**
 * お知らせコントローラー
 */
@Controller
@RequestMapping("systems/notices")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(final NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping
    public ModelAndView index(@ModelAttribute final NoticeSearchForm noticeSearchForm,
        @PageableDefault final Pageable pageable, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var noticePage = noticeService.search(noticeSearchForm.title(), noticeSearchForm.startAt(),
                noticeSearchForm.endAt(), pageable, userInfoDto.account());
        return new ModelAndView("systems/notices/index").addObject(noticePage);
    }
}
