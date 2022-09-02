package jp.co.project.planets.earthly.webapp.emuns;

import jp.co.project.planets.earthly.core.enums.IErrorCode;

import static jp.co.project.planets.earthly.webapp.emuns.ErrorMessageKey.*;

/**
 * error code
 */
public enum ErrorCode implements IErrorCode {
    EWA4XX001("EWA4XX001", ""),
    /** 対象ユーザーは存在しません。 */
    EWA4XX002("EWA4XX002", NOTFOUND_USER),
    /** 対象のユーザーは閲覧することはできません。 */
    EWA4XX003("EWA4XX003", FORBIDDEN_USER),
    /** ユーザー登録登録の権限がありません。 */
    EWA4XX004("EWA4XX004", FORBIDDEN_ENTRY_USER);

    /** エラーコード */
    private final String code;

    /** エラーメッセージキー */
    private final String messageKey;

    ErrorCode(final String code, final String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessageKey() {
        return this.messageKey;
    }
}
