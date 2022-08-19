package jp.co.project.planets.earthly.webapp.exception;

import jp.co.project.planets.earthly.core.enums.IErrorCode;
import jp.co.project.planets.earthly.core.exception.AbstractBaseException;
import org.springframework.http.HttpStatus;

/**
 * forbidden exception
 */
public class ForbiddenException extends AbstractBaseException {

    /**
     * new instance forbidden exception
     *
     * @param errorCode
     *         error code
     * @param messageKeyArgs
     *         message key args
     */
    public ForbiddenException(final IErrorCode errorCode, final String... messageKeyArgs) {
        this(null, errorCode, messageKeyArgs);
    }

    /**
     * new instance not found exception
     *
     * @param message
     *         detail messages
     * @param errorCode
     *         error code
     * @param messageKeyArgs
     *         message key args
     */
    public ForbiddenException(final String message, final IErrorCode errorCode, final String... messageKeyArgs) {
        this(message, null, errorCode, messageKeyArgs);
    }

    /**
     * new instance not found exception
     *
     * @param message
     *         detail messages
     * @param cause
     *         caused by
     * @param errorCode
     *         error code
     * @param messageKeyArgs
     *         message key args
     */
    public ForbiddenException(final String message, final Throwable cause, final IErrorCode errorCode,
                              final String... messageKeyArgs) {
        super(message, cause, HttpStatus.NOT_FOUND.value(), errorCode, messageKeyArgs);
    }
}
