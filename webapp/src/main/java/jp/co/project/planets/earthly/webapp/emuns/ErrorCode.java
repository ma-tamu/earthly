package jp.co.project.planets.earthly.webapp.emuns;

import static jp.co.project.planets.earthly.webapp.emuns.ErrorMessageKey.*;

import jp.co.project.planets.earthly.core.enums.Code;

/**
 * error code
 */
public enum ErrorCode implements Code {
    EWA4XX001(""),
    /** 対象ユーザーは存在しません。 */
    EWA4XX002(NOTFOUND_USER),
    /** 対象のユーザーは閲覧することはできません。 */
    EWA4XX003(FORBIDDEN_USER),
    /** ユーザー登録の権限がありません。 */
    EWA4XX004(FORBIDDEN_ENTRY_USER),
    /** ユーザー編集の権限がありません。 */
    EWA4XX005(FORBIDDEN_EDIT_USER),
    /** 所属会社の編集ができません。 */
    EWA4XX006(NOT_MODIFY_BELONG_COMPANY),
    /** 新しいパスワードと新しいパスワードの再入力が異なります。 */
    EWA4XX007(NEW_PASSWORD_MISMATCH),
    /** ユーザー削除の権限がありません。 */
    EWA4XX008(FORBIDDEN_DELETE_USER),
    /** 割り当てられないロールが含まれています。 */
    EWA4XX009(ACCESS_DENIED_ASSIGN_ROLE),
    /** 解除できないロールが含まれています。 */
    EWA4XX010(ACCESS_DENIED_UNASSIGNED_ROLE),
    /** 新しいパスワードが一致しません。 */
    EWA4XX011(CURRENT_PASSWORD),
    /** 新しいパスワードが一致しません。 */
    EWA4XX012(MISMATCH_NEW_PASSWORD),

    /** 8文字以上入力してください。 */
    EWA4XX013(PASSWORD_LENGTH),
    /**
     * 大文字、小文字の英字、数字、記号(ASCII 標準文字のみ)を組み合わせてください。<br>
     * アクセント記号やアクセント記号付き文字は使用できません。
     */
    EWA4XX014(PASSWORD_NEW_INSUFFICIENT_STRENGTH),
    /** 入力されたメールアドレスが正しくありません。 */
    EWA4XX015(NOT_FOUND_MAIL_USER),
    /** OAuthクライアントを閲覧することはできません。 */
    EWA4XX016(FORBIDDEN_CLIENT),
    /** OAuthクライアント登録の権限がありません。 */
    EWA4XX017(FORBIDDEN_ENTRY_CLIENT),
    /** 対象OAuthクライアントは存在しません。 */
    EWA4XX018(NOTFOUND_CLIENT),
    /** OAuthクライアントを編集することはできません。 */
    EWA4XX019(FORBIDDEN_EDIT_CLIENT),
    /** 会社登録の権限がありません。 */
    EWA4XX020(FORBIDDEN_ENTRY_COMPANY),
    /** 削除対象に無効なリダイレクトURIが含まれています。 */
    EWA4XX021(CLIENT_REDIRECT_URI_REMOVE_INVALID),
    /** 割り当て対象のユーザーは既にOAuthクライアントの管理者になっています。 */
    EWA4XX022(CLIENT_MANAGEMENT_USER_ALREADY_REGISTERED),
    /** 選択されたOAuthクライアントの管理者を閲覧できないため、解除できません。 */
    EWA4XX023(CLIENT_MANAGEMENT_USER_FORBIDDEN),
    /** 所属国が存在しません。 */
    EWA4XX024(NOT_FOUND_CUNTRY),
    /** 対象の会社は存在しません。 */
    EWA4XX025(NOT_FOUND_COMPANY),
    /** 編集する権限がありません。 */
    EWA4XX026(NOT_HAVE_PERMISSION_EDIT),
    /** 既に割り当て済みのユーザーが存在しています。 */
    EWA4XX027(COMPANY_ASSIGNED_MANAGEMENT_USER),
    /** 閲覧できないユーザーが含まれています。 */
    EWA4XX028(ASSIGN_USER_ACCESS_DENIED),
    /** 未割り当てのユーザーが存在しています。 */
    EWA4XX029(COMPANY_UNASSIGNED_MANAGEMENT_USER),
    /** グループ登録の権限がありません。 */
    EWA4XX030(FORBIDDEN_ENTRY_GROUP),
    /** 対象のグループは存在しません。 */
    EWA4XX031(NOTFOUND_GROUP),
    /** 対象のロールは存在しません。 */
    EWA4XX032(NOTFOUND_ROLE),
    /** ロール登録の権限がありません。 */
    EWA4XX033(FORBIDDEN_ENTRY_ROLE),
    /** ロール編集の権限がありません。 */
    EWA4XX034(FORBIDDEN_EDIT_ROLE),
    /** ロール削除の権限がありません。 */
    EWA4XX035(FORBIDDEN_DELETE_ROLE),
    /** 割り当てできないパーミッションがあります。 */
    EWA4XX036(ROLE_ASSIGN_PERMISSION_MISMATCH),
    /** 既に割り当て済みのパーミッションが含まれています。 */
    EWA4XX037(ROLE_ASSIGN_PERMISSION_ALREADY),
    /** 存在しないパーミッションが含まれています。 */
    EWA4XX038(ROLE_UNASSIGN_PERMISSION_NOT_FOUND),
    /** 割り当てられていないパーミッションがあります。 */
    EWA4XX039(ROLE_UNASSIGN_PERMISSION_MISMATCH),
    /** パーミッションリストを閲覧することはできません。 */
    EWA4XX040(FORBIDDEN_PERMISSION_LIST),
    /** 対象のパーミッションは存在しません。 */
    EWA4XX041(NOTFOUND_PERMISSION),
    /** 割り当てられていないロールが含まれています。 */
    EWA4XX042(PERMISSION_UNASSIGNED_ROLE),
    /** 割り当てできないロールが含まれてます。 */
    EWA4XX043(PERMISSION_ASSIGN_ROLE_MISMATCH),
    /** 既に割り当て済みのロールが含まれています。 */
    EWA4XX044(PERMISSION_ASSIGN_ROLE_ALREADY),
    /** {0}の登録に失敗しました。 */
    EWA5XX001(FAILED_INSERT),
    EWA5XX999(UNEXPECTED);

    /** エラーメッセージキー */
    private final String messageKey;

    ErrorCode(final String messageKey) {
        this.messageKey = messageKey;
    }

    @Override
    public String getCode() {
        return this.toString();
    }

    @Override
    public String getMessageKey() {
        return this.messageKey;
    }
}
