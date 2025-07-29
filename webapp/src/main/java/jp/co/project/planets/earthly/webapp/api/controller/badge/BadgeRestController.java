package jp.co.project.planets.earthly.webapp.api.controller.badge;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import jp.co.project.planets.earthly.webapp.api.response.NoticeResponse;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.service.BadgeService;

@RestController
@RequestMapping("api/badges")
public class BadgeRestController {

    private final BadgeService badgeService;

    public BadgeRestController(final BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    @GetMapping("notices")
    public NoticeResponse getNotice(
        @AuthenticationPrincipal final EarthlyUserInfoDto earthlyUserInfoDto) {
        final var noticeList = badgeService.getNotices(earthlyUserInfoDto.account());
        return new NoticeResponse(noticeList.size(), noticeList);
    }

    @PostMapping("notices/update")
    public ResponseEntity<String> updateNoticeCookie(final HttpServletResponse response,
        @AuthenticationPrincipal final EarthlyUserInfoDto earthlyUserInfoDto) {
        badgeService.updateNotice(response, earthlyUserInfoDto.account());
        return ResponseEntity.ok("OK");
    }
}
