package jp.co.project.planets.earthly.webapp.emuns;

/**
 * error message key
 */
public final class ErrorMessageKey {

    /** 対象ユーザーは存在しません。 */
    public static final String NOTFOUND_USER = "exception.notfound.user";

    /** 対象のユーザーは閲覧することはできません。 */
    public static final String FORBIDDEN_USER = "exception.forbidden.user";

    /** ユーザー登録の権限がありません。 */
    public static final String FORBIDDEN_ENTRY_USER = "exception.forbidden.entry.user";
    /** ユーザー編集の権限がありません。 */
    public static final String FORBIDDEN_EDIT_USER = "exception.forbidden.edit.user";
    /** ユーザー削除の権限がありません。 */
    public static final String FORBIDDEN_DELETE_USER = "exception.forbidden.delete.user";
    /** 所属会社の編集ができません。 */
    public static final String NOT_MODIFY_BELONG_COMPANY = "exception.modify.company";
    /** 新しいパスワードと新しいパスワードの再入力が一致しません。 */
    public static final String NEW_PASSWORD_MISMATCH = "exception.password.new.mismatch";

    /** 半角英数字で入力してください。 */
    public static final String VALIDATION_ALPHANUMERIC = "exception.validation.alphanumeric";
    /** 割り当てるロールを選択してください。 */
    public static final String NOT_SELECTION_ASSIGN_ROLE = "exception.user.assign.role.not.selection";
    /** 割り当てられないロールが含まれています。 */
    public static final String ACCESS_DENIED_ASSIGN_ROLE = "exception.user.assign.role.access.denied";

    /** {0}の登録に失敗しました。 */
    public static final String FAILED_INSERT = "exception.failed.insert";

}
