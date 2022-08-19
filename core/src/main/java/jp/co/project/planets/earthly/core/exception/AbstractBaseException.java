package jp.co.project.planets.earthly.core.exception;

import jp.co.project.planets.earthly.core.enums.IErrorCode;

/**
 * abstract base exception
 */
public abstract class AbstractBaseException extends RuntimeException {

    /** HTTPステータス */
    private final int httpStatus;

    /** エラーコード */
    private final IErrorCode errorCode;

    /** エラーメッセージキーの引数 */
    private final String[] messageKeyArgs;

    /**
     * new instance exception
     *
     * @param httpStatus
     *         http status
     * @param errorCode
     *         error code
     * @param messageKeyArgs
     *         message key args
     */
    protected AbstractBaseException(final int httpStatus, final IErrorCode errorCode, final String[] messageKeyArgs) {
        this(null, null, httpStatus, errorCode, messageKeyArgs);
    }

    /**
     * new instance exception
     *
     * @param message
     *         exception detail message
     * @param httpStatus
     *         http status
     * @param errorCode
     *         error code
     * @param messageKeyArgs
     *         message key args
     */
    protected AbstractBaseException(final String message, final int httpStatus, final IErrorCode errorCode,
            final String[] messageKeyArgs) {
        this(message, null, httpStatus, errorCode, messageKeyArgs);
    }


    /**
     * new instance exception
     *
     * @param message
     *         exception detail message
     * @param cause
     *         cause
     * @param httpStatus
     *         http status
     * @param errorCode
     *         error code
     * @param messageKeyArgs
     *         message key args
     */
    protected AbstractBaseException(final String message, final Throwable cause, final int httpStatus,
            final IErrorCode errorCode, final String[] messageKeyArgs) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.messageKeyArgs = messageKeyArgs;
    }

    /**
     * get http status
     *
     * @return http status
     */
    public int getHttpStatus() {
        return httpStatus;
    }

    /**
     * get error code
     *
     * @return error code
     */
    public IErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * message key args
     *
     * @return args
     */
    public String[] getMessageKeyArgs() {
        return messageKeyArgs;
    }
}
