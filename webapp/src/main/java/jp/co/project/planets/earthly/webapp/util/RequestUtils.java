package jp.co.project.planets.earthly.webapp.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jp.co.project.planets.earthly.webapp.constant.CookieName;

/**
 * request utils
 */
public final class RequestUtils {

    /**
     * get http servlet request
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * get http session
     *
     * @return HttpSession
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * get saved request
     *
     * @return DefaultSavedRequest
     */
    public static DefaultSavedRequest getSavedRequest() {
        return (DefaultSavedRequest) getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
    }

    /**
     * 認証前のURLを取得
     *
     * @return 認証前のURL
     */
    public static Optional<String> getSavedRequestRedirectUrl() {
        return Optional.ofNullable(getSavedRequest()).map(request -> {
            if (StringUtils.isBlank(request.getQueryString())) {
                return request.getRequestURI();
            }
            return request.getRequestURI() + "?" + request.getQueryString();
        });
    }

    public static Optional<Cookie> getCookie(final String name) {
        return Stream.of(getRequest().getCookies()).filter(cookie -> Strings.CS.equals(name, cookie.getName()))
                .findFirst();
    }

    public static LocalDateTime getLastNoticeDate() {
        return getCookie(CookieName.NOTICE)
                .map(cookie -> LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(cookie.getValue())),
                        ZoneId.systemDefault()))
                .orElse(LocalDateTime.ofInstant(Instant.ofEpochMilli(1753260523L), ZoneId.systemDefault()));
    }
}
