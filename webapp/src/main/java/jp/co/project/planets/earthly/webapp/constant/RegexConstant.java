package jp.co.project.planets.earthly.webapp.constant;

import java.util.regex.Pattern;

/**
 * 正規表現定数
 */
public final class RegexConstant {

    /** パスワード強度の正規表現 */
    public static final String PASSWORD_STRENGTH = " ^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]?/*~$^+=<>&%]).{8,}$";

    public static final Pattern PASSWORD_STRENGTH_PATTERN = Pattern.compile(PASSWORD_STRENGTH);

    private RegexConstant() {
    }
}
