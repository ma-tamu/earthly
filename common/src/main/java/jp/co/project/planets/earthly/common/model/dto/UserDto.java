package jp.co.project.planets.earthly.common.model.dto;

import java.util.UUID;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import jp.co.project.planets.earthly.db.entity.User;

/**
 * user dto
 *
 * @param loginId
 *            ログインID
 * @param name
 *            ユーザー名
 * @param mail
 *            メールアドレス
 * @param gender
 *            性別
 * @param company
 *            所属会社ID
 * @param companyName
 *            所属会社名
 */
public record UserDto(String loginId, String name, String mail, String gender, String language, String timezone,
        String company, String companyName, Boolean lockout, Boolean is2fa) {

    /**
     * convert to entity
     *
     * @return user
     */
    public User toEntity() {
        final var id = UUID.randomUUID().toString().replace("-", "");
        final var user = new User();
        user.setId(id);
        user.setLoginId(loginId);
        user.setPassword(StringUtils.EMPTY);
        user.setName(name);
        user.setMail(mail);
        user.setGender(gender);
        user.setLanguage(language);
        user.setTimezone(timezone);
        user.setCompanyId(company);
        user.setLockout(BooleanUtils.isTrue(lockout));
        user.setTwoFactorAuthentication(is2fa);
        user.setIsDeleted(false);
        return user;
    }
}
