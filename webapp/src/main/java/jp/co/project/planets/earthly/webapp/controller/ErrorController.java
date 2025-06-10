package jp.co.project.planets.earthly.webapp.controller;

import java.time.Instant;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping({ "${server.error.path:${error.path:/error}}" })
public class ErrorController extends AbstractErrorController {

    private static final Logger log = LoggerFactory.getLogger(ErrorController.class);

    public ErrorController(final ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @RequestMapping(produces = { "text/html" })
    public ModelAndView error(final HttpServletRequest request) {
        final var serial = Instant.now().toEpochMilli();
        Optional.ofNullable((Exception) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION))
                .map(ExceptionUtils::getStackTrace)
                .ifPresent(stacktrace -> log.error("serial:{} stack trace:{}", serial, stacktrace));
        final var springSecurityExceptionOptional = Optional
                .ofNullable((AuthenticationException) request.getAttribute("SPRING_SECURITY_LAST_EXCEPTION"));
        if (springSecurityExceptionOptional.isPresent()) {
            final var authenticationException = springSecurityExceptionOptional.get();
            log.error("serial:{} message:{}", serial, authenticationException.getMessage());
            return new ModelAndView("login");
        }
        return new ModelAndView("errors/500");
    }
}
