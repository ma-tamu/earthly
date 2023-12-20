package jp.co.project.planets.earthly.schema.emuns;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

/**
 * scope enum
 */
public enum Scope {
    OPENID("a85c1c6665fc11ec8a0b0242ac120003", "openid"),
    ME("15b4a732660d11ec8a0b0242ac120003", "me"),
    USER("99842dbb497011eebccc0242ac120003", "user"),
    COMPANY("696959793a9711ee95190242ac120004", "company"),
    CLIENT("7319820b3a9711ee95190242ac120004", "client");

    private final String id;

    private final String value;

    Scope(final String id, final String value) {
        this.id = id;
        this.value = value;
    }

    /**
     * idからScopeへ変換
     *
     * @param id
     *            スコープID
     * @return Scope
     */
    public static Scope of(final String id) {
        return Arrays.stream(Scope.values()).filter(it -> StringUtils.equals(it.getId(), id)).findFirst().orElse(null);
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
