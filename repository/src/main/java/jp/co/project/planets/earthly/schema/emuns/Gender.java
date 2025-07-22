package jp.co.project.planets.earthly.schema.emuns;

import java.util.Arrays;

import org.apache.commons.lang3.Strings;

/**
 * gender enum
 */
public enum Gender {

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
     *            initial gender value
     */
    Gender(final String value) {
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
     *            gender value
     * @return GenderEnum
     */
    public static Gender of(final String value) {
        return Arrays.stream(Gender.values()).filter(
                it -> Strings.CS.equals(value, it.getValue())).findFirst().orElse(null);
    }
}
