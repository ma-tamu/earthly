package jp.co.project.planets.earthly.webapp.exception;

import jp.co.project.planets.earthly.core.exception.AbstractBaseException;
import jp.co.project.planets.earthly.webapp.constant.ModelKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * global exception handler
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public GlobalExceptionHandler(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handler(final Exception exception) {
        log.error(exception.getMessage(), exception);
        final var modelAndView = new ModelAndView("error/500");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }

    @ExceptionHandler({ForbiddenException.class, NotFoundException.class})
    public ModelAndView baseExceptionHandler(final AbstractBaseException baseException) {
        final var modelAndView = new ModelAndView("error/404");
        final var httpStatus = HttpStatus.valueOf(baseException.getHttpStatus());
        modelAndView.setStatus(httpStatus);

        final var locale = LocaleContextHolder.getLocale();
        final var errorCode = baseException.getErrorCode();
        final var message = messageSource.getMessage(errorCode.getMessageKey(), baseException.getMessageKeyArgs(),
                locale);
        modelAndView.addObject(ModelKey.MESSAGE, message);
        return modelAndView;
    }
}
