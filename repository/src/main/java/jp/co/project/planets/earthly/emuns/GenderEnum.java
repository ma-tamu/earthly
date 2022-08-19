package jp.co.project.planets.earthly.emuns;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * gender enum
 */
public enum GenderEnum {

    /** 男性 */
    MALE("M"),
    /** 女性 */
    FEMALE("F"),
    /** 無回答 */
    OTHER("-");

    /** short gender */
    private final String value;

    /**
     * new instance enum
     *
     * @param value
     *         initial gender value
     */
    GenderEnum(final String value) {
        this.value = value;
    }

    /**
     * get initial gender value
     *
     * @return initial gender value
     */
    public String getValue() {
        return value;
    }

    /**
     * value of gender enum
     *
     * @param value
     *         gender value
     * @return GenderEnum
     */
    public static GenderEnum of(final String value) {
        return Arrays.stream(GenderEnum.values()).filter(
                it -> StringUtils.equals(value, it.getValue())).findFirst().orElse(null);
    }
}
