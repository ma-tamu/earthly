package jp.co.project.planets.earthly.emuns;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * permission enum
 */
public enum PermissionEnum {

    /** ユーザー追加 */
    ADD_USER("f212985ca90d11ec88720242ac120003"),
    /** 会社追加 */
    ADD_COMPANY("f212996aa90d11ec88720242ac120003"),
    /** 分室追加 */
    ADD_ALL_BRANCH("f2129a10a90d11ec88720242ac120003"),
    /** 所属会社の分室追加 */
    ADD_MY_COMPANY_BRANCH("f2129ab3a90d11ec88720242ac120003"),
    /** お知らせ追加 */
    ADD_NOTICE("f2129b64a90d11ec88720242ac120003"),
    /** OAuthクライアント追加 */
    ADD_OAUTH_CLIENT("f2129c0aa90d11ec88720242ac120003"),
    /** ロール追加 */
    ADD_ROLE("f2129caea90d11ec88720242ac120003"),
    /** ユーザー編集 */
    EDIT_USER("f2129d52a90d11ec88720242ac120003"),
    /** 会社編集 */
    EDIT_COMPANY("f2129df8a90d11ec88720242ac120003"),
    /** 分室編集 */
    EDIT_ALL_BRANCH("f2129e9aa90d11ec88720242ac120003"),
    /** 所属会社の分室編集 */
    EDIT_MY_COMPANY_BRANCH("f2129f3ea90d11ec88720242ac120003"),
    /** お知らせ編集 */
    EDIT_NOTICE("f2129fe3a90d11ec88720242ac120003"),
    /** OAuthクライアント編集 */
    EDIT_OAUTH_CLIENT("f212a0ada90d11ec88720242ac120003"),
    /** ロール編集 */
    EDIT_ROLE("f212a157a90d11ec88720242ac120003"),
    /** 全てのユーザーを閲覧 */
    VIEW_ALL_USER("f212a1fca90d11ec88720242ac120003"),
    /** 全ての会社を閲覧 */
    VIEW_ALL_COMPANY("f212a2a5a90d11ec88720242ac120003"),
    /** 全ての分室を閲覧 */
    VIEW_ALL_BRANCH("f212a34aa90d11ec88720242ac120003"),
    /** 全てのロールを閲覧 */
    VIEW_ALL_ROLE("f212a3f3a90d11ec88720242ac120003"),
    /** 全てのパーミッションを閲覧 */
    VIEW_ALL_PERMISSION("f212a499a90d11ec88720242ac120003"),
    /** 全てのOAuthクライアントを閲覧 */
    VIEW_ALL_OAUTH_CLIENT("a4f8f0b6a90e11ec88720242ac120003");

    /** permission id */
    private final String id;

    /**
     * new instance permission enum
     *
     * @param id
     *         permission id
     */
    PermissionEnum(final String id) {
        this.id = id;
    }

    /**
     * パーミッションIDをEnumへ
     *
     * @param id
     *         permission id
     * @return permission enum
     * @throws RuntimeException
     *         enumへ変換できない場合は発生。
     */
    public static PermissionEnum of(final String id) {
        return Arrays.stream(PermissionEnum.values()) //
                .filter(it -> StringUtils.equals(it.getId(), id)).findFirst() //
                .orElseThrow(() -> new RuntimeException(String.format("permission not found. id:%s", id)));
    }

    /**
     * getter id
     *
     * @return id
     */
    public String getId() {
        return id;
    }
}
