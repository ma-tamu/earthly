package jp.co.project.planets.earthly.core.enums;

/**
 * error code
 */
public interface IErrorCode {

    /**
     * get error code
     *
     * @return error code
     */
    String getCode();

    /**
     * get message key
     *
     * @return message key
     */
    String getMessageKey();
}
