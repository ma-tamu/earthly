package jp.co.project.planets.earthly.webapp.controller.system;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.service.NoticeService;

@Controller
@RequestMapping("systems/notices/{id}")
public class NoticeDetailController {

    private final NoticeService noticeService;

    public NoticeDetailController(final NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping
    public ModelAndView index(@PathVariable("id") final String id, final Model model,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var notice = noticeService.get(id, userInfoDto.account());
        return new ModelAndView("systems/notices/detail", "notice", notice).addAllObjects(model.asMap());
    }
}
