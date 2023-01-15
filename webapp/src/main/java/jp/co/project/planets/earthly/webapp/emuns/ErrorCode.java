package jp.co.project.planets.earthly.webapp.emuns;

import static jp.co.project.planets.earthly.webapp.emuns.ErrorMessageKey.*;

import jp.co.project.planets.earthly.core.enums.Code;

/**
 * error code
 */
public enum ErrorCode implements Code {
    EWA4XX001("EWA4XX001", ""),
    /** 対象ユーザーは存在しません。 */
    EWA4XX002("EWA4XX002", NOTFOUND_USER),
    /** 対象のユーザーは閲覧することはできません。 */
    EWA4XX003("EWA4XX003", FORBIDDEN_USER),
    /** ユーザー登録の権限がありません。 */
    EWA4XX004("EWA4XX004", FORBIDDEN_ENTRY_USER),
    /** ユーザー編集の権限がありません。 */
    EWA4XX005("EWA4XX005", FORBIDDEN_EDIT_USER),
    /** 所属会社の編集ができません。 */
    EWA4XX006("EWA4XX005", NOT_MODIFY_BELONG_COMPANY),
    /** {0}の登録に失敗しました。 */
    EWA5XX001("EWA5XX001", FAILED_INSERT),
    ;

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
