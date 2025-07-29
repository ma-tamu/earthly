package jp.co.project.planets.earthly.webapp.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletResponse;
import jp.co.project.planets.earthly.core.account.Account;
import jp.co.project.planets.earthly.schema.db.entity.Notice;
import jp.co.project.planets.earthly.schema.db.entity.OpenNotice;
import jp.co.project.planets.earthly.schema.repository.NoticeRepository;
import jp.co.project.planets.earthly.schema.repository.OpenNoticeRepository;
import jp.co.project.planets.earthly.webapp.constant.CookieName;
import jp.co.project.planets.earthly.webapp.util.CookieUtils;
import jp.co.project.planets.earthly.webapp.util.RequestUtils;

@Service
public class BadgeService {

    private final NoticeRepository noticeRepository;
    private final OpenNoticeRepository openNoticeRepository;

    private static final long MIN_NOTICE_TIME = 1579564800L;

    public BadgeService(final NoticeRepository noticeRepository, final OpenNoticeRepository openNoticeRepository) {
        this.noticeRepository = noticeRepository;
        this.openNoticeRepository = openNoticeRepository;
    }

    @Transactional
    public List<Notice> getNotices(final Account account) {
        final long openedNoticeTime = RequestUtils.getCookie(CookieName.NOTICE)
                .map(cookie -> Long.parseLong(cookie.getValue())).orElse(MIN_NOTICE_TIME);
        final var lastNoticeAt = LocalDateTime.ofEpochSecond(openedNoticeTime, 0, ZoneOffset.ofHours(9));
        final var openNoticeAt = openNoticeRepository.findByUserId(account.id()).map(OpenNotice::getOpenedAt)
                .orElse(LocalDateTime.MIN);
        final var dateTime = lastNoticeAt.isAfter(openNoticeAt) ? lastNoticeAt : openNoticeAt;
        return noticeRepository.findUnreadNoticeByPublicationDate(dateTime);
    }

    @Transactional
    public void updateNotice(final HttpServletResponse response, final Account account) {
        final long openedNoticeTime = RequestUtils.getCookie(CookieName.NOTICE)
                .map(cookie -> Long.parseLong(cookie.getValue())).orElse(MIN_NOTICE_TIME);
        final var lastNoticeAt = LocalDateTime.ofEpochSecond(openedNoticeTime, 0, ZoneOffset.ofHours(9));
        final var noticeList = noticeRepository.findUnreadNoticeByPublicationDate(lastNoticeAt);
        final var notice = noticeList.getFirst();
        final var openNoticeId = openNoticeRepository.findByUserId(account.id()).map(OpenNotice::getId).orElse(null);
        final var openNotice = new OpenNotice(openNoticeId, account.id(), notice.getStartAt());
        if (StringUtils.isNotBlank(openNoticeId)) {
            openNoticeRepository.update(openNotice);
        } else {
            openNoticeRepository.insert(openNotice);
        }
        final var latestStartSecond = String.valueOf(notice.getStartAt().toEpochSecond(ZoneOffset.ofHours(9)));
        final var cookie = CookieUtils.generate(CookieName.NOTICE, latestStartSecond);
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
