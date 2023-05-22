package jp.co.project.planets.earthly.auth.filter;

import java.io.IOException;

import org.slf4j.MDC;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class RequestFilter extends GenericFilterBean {
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {

        try {
            MDC.put("request", ((HttpServletRequest) request).getRequestURI());
            chain.doFilter(request, response);
        } finally {
            MDC.remove("request");
        }
    }
}
