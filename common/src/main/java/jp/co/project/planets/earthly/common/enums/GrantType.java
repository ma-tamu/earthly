package jp.co.project.planets.earthly.common.enums;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import jakarta.annotation.Nonnull;

/**
 * Grant Type enum
 */
public enum GrantType {

    /**
     * クライアントクレデンシャルズ
     */
    CLIENT_CREDENTIALS("092519c3660d11ec8a0b0242ac120003", "client credentials"),

    /**
     * パスワード
     */
    PASSWORD("0f4c8f7b660d11ec8a0b0242ac120003", "password"),

    /**
     * 認可コード
     */
    AUTHORIZATION_CODE("1b6a0d2a65f911ec8a0b0242ac120003", "authorization code"),

    /**
     * インプリシット
     */
    IMPLICIT("f35c63dd660c11ec8a0b0242ac120003", "implicit"),

    /**
     * リフレッシュトークン
     */
    REFRESH_TOKEN("fdc24af6660c11ec8a0b0242ac120003", "refresh token"),
    ;

    private final String id;

    private final String value;

    GrantType(final String id, final String value) {
        this.id = id;
        this.value = value;
    }

    /**
     * idからGrantTypeに変換
     *
     * @param id
     *            grant type id
     * @return Grant Type
     */
    public static GrantType of(@Nonnull final String id) {
        return Arrays.stream(GrantType.values()).filter(it -> StringUtils.equals(id, it.getId())).findFirst()
                .orElse(null);
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
