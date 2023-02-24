package jp.co.project.planets.earthly.webapp.exception;

import org.springframework.http.HttpStatus;

import jp.co.project.planets.earthly.core.enums.Code;
import jp.co.project.planets.earthly.core.exception.AbstractBaseException;

/**
 * bad request exception
 */
public class BadRequestException extends AbstractBaseException {

    /**
     * new instance bad request exception
     *
     * @param errorCode
     *            error code
     * @param messageKeyArgs
     *            message key args
     */
    public BadRequestException(final Code errorCode, final String... messageKeyArgs) {
        this(null, errorCode, messageKeyArgs);
    }

    /**
     * new instance bad request exception
     *
     * @param message
     *            detail messages
     * @param errorCode
     *            error code
     * @param messageKeyArgs
     *            message key args
     */
    public BadRequestException(final String message, final Code errorCode, final String... messageKeyArgs) {
        this(message, null, errorCode, messageKeyArgs);
    }

    /**
     * new instance bad request exception
     *
     * @param message
     *            detail messages
     * @param cause
     *            caused by
     * @param errorCode
     *            error code
     * @param messageKeyArgs
     *            message key args
     */
    public BadRequestException(final String message, final Throwable cause, final Code errorCode,
            final String... messageKeyArgs) {
        super(message, cause, HttpStatus.NOT_FOUND.value(), errorCode, messageKeyArgs);
    }
}
