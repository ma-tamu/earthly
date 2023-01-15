package jp.co.project.planets.earthly.webapp.exception;

import org.springframework.http.HttpStatus;

import jp.co.project.planets.earthly.core.enums.Code;
import jp.co.project.planets.earthly.core.exception.AbstractBaseException;

/**
 * forbidden exception
 */
public class ForbiddenException extends AbstractBaseException {

    /**
     * new instance forbidden exception
     *
     * @param errorCode
     *            error code
     * @param messageKeyArgs
     *            message key args
     */
    public ForbiddenException(final Code errorCode, final String... messageKeyArgs) {
        this(null, errorCode, messageKeyArgs);
    }

    /**
     * new instance not found exception
     *
     * @param message
     *            detail messages
     * @param errorCode
     *            error code
     * @param messageKeyArgs
     *            message key args
     */
    public ForbiddenException(final String message, final Code errorCode, final String... messageKeyArgs) {
        this(message, null, errorCode, messageKeyArgs);
    }

    /**
     * new instance not found exception
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
    public ForbiddenException(final String message, final Throwable cause, final Code errorCode,
            final String... messageKeyArgs) {
        super(message, cause, HttpStatus.NOT_FOUND.value(), errorCode, messageKeyArgs);
    }
}
