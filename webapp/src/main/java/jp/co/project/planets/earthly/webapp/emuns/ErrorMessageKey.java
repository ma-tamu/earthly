package jp.co.project.planets.earthly.webapp.emuns;

/**
 * error message key
 */
public final class ErrorMessageKey {

    /** 対象ユーザーは存在しません。 */
    public static final String NOTFOUND_USER = "exception.notfound.user";
    /** 対象OAuthクライアントは存在しません。 */
    public static final String NOTFOUND_CLIENT = "exception.notfound.client";
    /** 対象のユーザーは閲覧することはできません。 */
    public static final String FORBIDDEN_USER = "exception.forbidden.user";

    /** ユーザー登録の権限がありません。 */
    public static final String FORBIDDEN_ENTRY_USER = "exception.forbidden.entry.user";
    /** ユーザー編集の権限がありません。 */
    public static final String FORBIDDEN_EDIT_USER = "exception.forbidden.edit.user";
    /** ユーザー削除の権限がありません。 */
    public static final String FORBIDDEN_DELETE_USER = "exception.forbidden.delete.user";
    /** OAuthクライアントを閲覧することはできません。 */
    public static final String FORBIDDEN_CLIENT = "exception.forbidden.client";
    /** OAuthクライアント登録の権限がありません。 */
    public static final String FORBIDDEN_ENTRY_CLIENT = "exception.forbidden.entry.client";
    /** OAuthクライアントを編集することはできません。 */
    public static final String FORBIDDEN_EDIT_CLIENT = "exception.forbidden.edit.client";
    /** 会社登録の権限がありません。 */
    public static final String FORBIDDEN_ENTRY_COMPANY = "exception.forbidden.entry.company";
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
    /** 解除できないロールが含まれています。 */
    public static final String ACCESS_DENIED_UNASSIGNED_ROLE = "exception.user.unassigned.role.access.denied";

    /** {0}の登録に失敗しました。 */
    public static final String FAILED_INSERT = "exception.failed.insert";

    /** 現在のパスワードが誤っています。 */
    public static final String CURRENT_PASSWORD = "exception.user.password.current";

    /** 新しいパスワードが一致しません。 */
    public static final String MISMATCH_NEW_PASSWORD = "exception.user.password.new.mismatch";

    /** 8文字以上入力してください。 */
    public static final String PASSWORD_LENGTH = "exception.user.password.new.length";

    /**
     * 大文字、小文字の英字、数字、記号(ASCII 標準文字のみ)を組み合わせてください。<br>
     * アクセント記号やアクセント記号付き文字は使用できません。
     */
    public static final String PASSWORD_NEW_INSUFFICIENT_STRENGTH = "exception.user.password.new.insufficient.strength";

    /** 入力されたメールアドレスが正しくありません。 */
    public static final String NOT_FOUND_MAIL_USER = "exception.notfound.forgot.password.mail";

    /** 削除対象に無効なリダイレクトURIが含まれています。 */
    public static final String CLIENT_REDIRECT_URI_REMOVE_INVALID = "exception.client.redirect.uri.remove.invalid";

    /** 割り当て対象のユーザーは既にOAuthクライアントの管理者になっています。 */
    public static final String CLIENT_MANAGEMENT_USER_ALREADY_REGISTERED = "exception.client.management.user.already.registered";

    /** 選択されたOAuthクライアントの管理者を閲覧できないため、解除できません。 */
    public static final String CLIENT_MANAGEMENT_USER_FORBIDDEN = "exception.client.management.user.forbidden";

    /** 所属国が存在しません。 */
    public static final String NOT_FOUND_CUNTRY = "exception.company.notfound.country";

    /** 対象の会社は存在しません。 */
    public static final String NOT_FOUND_COMPANY = "exception.notfound.company";

    /** 編集する権限がありません。 */
    public static final String NOT_HAVE_PERMISSION_EDIT = "exception.not.have.permission.edit";

    /** 既に割り当て済みのユーザーが存在しています。 */
    public static final String COMPANY_ASSIGNED_MANAGEMENT_USER = "exception.company.assigned.management.user";

    /** 未割り当てのユーザーが存在しています。 */
    public static final String COMPANY_UNASSIGNED_MANAGEMENT_USER = "exception.company.unassigned.management.user";

    /** 閲覧できないユーザーが含まれています。 */
    public static final String ASSIGN_USER_ACCESS_DENIED = "exception.assign.user.access.denied";

    /** グループ登録の権限がありません。 */
    public static final String FORBIDDEN_ENTRY_GROUP = "exception.forbidden.entry.group";

    /** 対象のグループは存在しません。 */
    public static final String NOTFOUND_GROUP = "exception.notfound.group";

    /** 対象のロールは存在しません。 */
    public static final String NOTFOUND_ROLE = "exception.notfound.role";
    /** ロール登録の権限がありません。 */
    public static final String FORBIDDEN_ENTRY_ROLE = "exception.forbidden.entry.role";
    /** ロール編集の権限がありません。 */
    public static final String FORBIDDEN_EDIT_ROLE = "exception.forbidden.edit.role";
    /** ロール削除の権限がありません。 */
    public static final String FORBIDDEN_DELETE_ROLE = "exception.forbidden.delete.role";
    /** 割り当てできないパーミッションがあります。 */
    public static final String ROLE_ASSIGN_PERMISSION_MISMATCH = "exception.role.assign.permission.mismatch";
    /** 既に割り当て済みのパーミッションが含まれています。 */
    public static final String ROLE_ASSIGN_PERMISSION_ALREADY = "exception.role.assign.permission.already";
    /** 存在しないパーミッションが含まれています。 */
    public static final String ROLE_UNASSIGN_PERMISSION_NOT_FOUND = "exception.role.unassign.permission.notfound";
    /** 割り当てられていないパーミッションがあります。 */
    public static final String ROLE_UNASSIGN_PERMISSION_MISMATCH = "exception.role.unassign.permission.mismatch";
    /** パーミッションリストを閲覧することはできません。 */
    public static final String FORBIDDEN_PERMISSION_LIST = "exception.forbidden.permission.list";
    /** 対象のパーミッションは存在しません。 */
    public static final String NOTFOUND_PERMISSION = "exception.notfound.permission";
    /** 割り当てられていないロールが含まれています。 */
    public static final String PERMISSION_UNASSIGNED_ROLE = "exception.permission.unassigned.role";
    /** 割り当てできないロールが含まれてます。 */
    public static final String PERMISSION_ASSIGN_ROLE_MISMATCH = "exception.permission.assign.role.mismatch";
    /** 既に割り当て済みのロールが含まれています。 */
    public static final String PERMISSION_ASSIGN_ROLE_ALREADY = "exception.permission.assign.role.already";
    /** 想定外のエラーが発生しました。 */
    public static final String UNEXPECTED = "exception.internal.unexpected";

    private ErrorMessageKey() {
    }
}
