package jp.co.project.planets.earthly.webapp.security.notice;

import java.io.IOException;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.service.EmphasisNoticeService;

@Controller
@RequestMapping("notices/emphasis")
public class EmphasisNoticeController {

    private final EmphasisNoticeService emphasisNoticeService;
    private final AuthenticationSuccessHandler successHandler;

    public EmphasisNoticeController(final EmphasisNoticeService emphasisNoticeService,
        final AuthenticationSuccessHandler successHandler) {
        this.emphasisNoticeService = emphasisNoticeService;
        this.successHandler = successHandler;
    }

    @GetMapping
    public ModelAndView index(final HttpServletResponse response,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var noticeList = emphasisNoticeService.getNotices(response, userInfoDto.account());
        return new ModelAndView("emphasis", "notices", noticeList);
    }

    @PostMapping
    public void close(final HttpServletRequest request, final HttpServletResponse response,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto)
            throws ServletException, IOException {
        emphasisNoticeService.updateSecurityContext(userInfoDto);
        successHandler.onAuthenticationSuccess(request, response,
                SecurityContextHolder.getContext().getAuthentication());
    }
}
