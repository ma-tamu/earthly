package jp.co.project.planets.earthly.webapp.util;

import java.util.Optional;

import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
        return Optional.ofNullable(getSavedRequest()).map(v -> {
            final var builder = UriComponentsBuilder.fromPath(v.getRequestURI());
            v.getParameterMap().forEach(builder::queryParam);
            return builder.build().toString();
        });
    }
}
