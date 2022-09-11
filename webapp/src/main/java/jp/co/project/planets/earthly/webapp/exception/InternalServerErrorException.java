package jp.co.project.planets.earthly.webapp.exception;

import jp.co.project.planets.earthly.core.enums.IErrorCode;
import jp.co.project.planets.earthly.core.exception.AbstractBaseException;
import org.springframework.http.HttpStatus;

/**
 * internal server error exception
 */
public class InternalServerErrorException extends AbstractBaseException {

    /**
     * new instance internal server error exception
     *
     * @param errorCode
     *         error code
     * @param messageKeyArgs
     *         message key args
     */
    public InternalServerErrorException(final IErrorCode errorCode, final String... messageKeyArgs) {
        this(null, errorCode, messageKeyArgs);
    }

    /**
     * new instance internal server error exception
     *
     * @param message
     *         detail messages
     * @param errorCode
     *         error code
     * @param messageKeyArgs
     *         message key args
     */
    public InternalServerErrorException(final String message, final IErrorCode errorCode,
            final String... messageKeyArgs) {
        this(message, null, errorCode, messageKeyArgs);
    }

    /**
     * new instance internal server error exception
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
    public InternalServerErrorException(final String message, final Throwable cause, final IErrorCode errorCode,
            final String... messageKeyArgs) {
        super(message, cause, HttpStatus.INTERNAL_SERVER_ERROR.value(), errorCode, messageKeyArgs);
    }
}
