package jp.co.project.planets.earthly.webapp.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jp.co.project.planets.earthly.core.account.Account;
import jp.co.project.planets.earthly.schema.db.entity.Notice;
import jp.co.project.planets.earthly.schema.db.entity.OpenNotice;
import jp.co.project.planets.earthly.schema.repository.NoticeRepository;
import jp.co.project.planets.earthly.schema.repository.OpenNoticeRepository;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;

@Service
public class EmphasisNoticeService {

    private final NoticeRepository noticeRepository;
    private final OpenNoticeRepository openNoticeRepository;

    public EmphasisNoticeService(final NoticeRepository noticeRepository,
        final OpenNoticeRepository openNoticeRepository) {
        this.noticeRepository = noticeRepository;
        this.openNoticeRepository = openNoticeRepository;
    }

    @Transactional
    public List<Notice> getNotices(final HttpServletResponse response, final Account account) {
        final var noticeList = noticeRepository
                .findUnreadEmphasisNoticeByPublicationDate(account.emphasis().lastEmphasisAt());
        final var latestNoticeOptional = noticeList.stream().findFirst();
        if (latestNoticeOptional.isPresent()) {
            final var latestNotice = latestNoticeOptional.get();
            final var id = openNoticeRepository.findByUserId(account.id()).map(OpenNotice::getId).orElse(null);
            final var openNotice = new OpenNotice(id, account.id(), latestNotice.getId(), LocalDateTime.now());
            if (StringUtils.isEmpty(id)) {
                openNoticeRepository.insert(openNotice);
            } else {
                openNoticeRepository.update(openNotice);
            }
            final var cookie = new Cookie("notice", String.valueOf(
                    latestNotice.getStartAt().toInstant((ZoneOffset) ZoneId.systemDefault()).toEpochMilli()));
            response.addCookie(cookie);
        }
        return noticeList;
    }

    /**
     * update security context
     *
     * @param userInfoDto
     *            ユーザー情報
     */
    public void updateSecurityContext(final EarthlyUserInfoDto userInfoDto) {

        final var earthlyUserInfoDto = new EarthlyUserInfoDto(userInfoDto.account(), userInfoDto.password(),
                userInfoDto.grantedAuthorities());
        final var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(earthlyUserInfoDto,
                userInfoDto.getPassword(), userInfoDto.getAuthorities());
        final var context = SecurityContextHolder.getContext();
        context.setAuthentication(usernamePasswordAuthenticationToken);
    }
}
