package jp.co.project.planets.earthly.common.test;

import java.util.Locale;

import jp.co.project.planets.earthly.core.enums.Timezone;
import jp.co.project.planets.earthly.schema.emuns.GenderEnum;

public final class UserConstant {

    public static final String OPERATOR = "OPERATOR";
    public static final String DUMMY_PASSWORD = "DUMMY";
    public static final String MFA_DUMMY_SECRET = "SECRET";

    private UserConstant() {
    }

    public static final class User01 {
        public static final String ID = "USER_ID_01";
        public static final String LOGIN_ID = "LOGIN_ID_01";
        public static final String NAME = "NAME_01";
        public static final String MAIL = "user01@example.com";
        public static final String GENDER = GenderEnum.MALE.getValue();
        public static final String LANGUAGE = Locale.JAPAN.getLanguage();
        public static final String TIMEZONE = Timezone.ASIA_TOKYO.getId();
        public static final String COMPANY = CompanyConstant.Company01.ID;
        public static final String COMPANY_NAME = CompanyConstant.Company01.NAME;
        public static final boolean LOCKOUT = false;
        public static final Boolean MFA = false;
    }
}
