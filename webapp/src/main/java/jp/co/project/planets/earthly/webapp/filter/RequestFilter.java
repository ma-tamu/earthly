package jp.co.project.planets.earthly.webapp.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.slf4j.MDC;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
 * request filter
 */
public class RequestFilter extends GenericFilterBean {
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {

        try {
            final var httpServletRequest = (HttpServletRequest) request;
            MDC.put("request", httpServletRequest.getRequestURI());
            final var requestParameter = httpServletRequest.getParameterMap().entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + Arrays.toString(entry.getValue()))
                    .collect(Collectors.joining(","));
            MDC.put("parameter", requestParameter);
            chain.doFilter(request, response);
        } finally {
            MDC.remove("request");
        }
    }
}
