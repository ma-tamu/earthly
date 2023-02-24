package jp.co.project.planets.earthly.webapp.filter;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;

/**
 * multi-factor authentication filter
 */
@Component
public class MultiFactorAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
            final FilterChain filterChain)
            throws ServletException, IOException {

        if (StringUtils.containsAny(request.getServletPath(), "/mfa", "css", "js", "img", "vendor")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()instanceof EarthlyUserInfoDto principal) {
            if (principal.tfa()) {
                if (!principal.tfaSuccessful()) {
                    response.sendRedirect("%s/mfa".formatted(request.getContextPath()));
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
