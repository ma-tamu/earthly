package jp.co.project.planets.earthly.common.model.dto;

import jp.co.project.planets.earthly.db.entity.User;

import java.util.UUID;

/**
 * user entry dto
 *
 * @param loginId
 *         ログインID
 * @param name
 *         ユーザー名
 * @param mail
 *         メールアドレス
 * @param gender
 *         性別
 * @param company
 *         所属会社ID
 * @param companyName
 *         所属会社名
 */
public record UserEntryDto(String loginId, String name, String mail, String gender, String company,
                           String companyName) {

    /**
     * convert to entity
     *
     * @return user
     */
    public User toEntity() {
        final var id = UUID.randomUUID().toString();
        final var user = new User();
        user.setId(id);
        user.setLoginId(loginId);
        user.setName(name);
        user.setMail(mail);
        user.setGender(gender);
        user.setCompanyId(company);
        user.setLockout(false);
        user.setIsDeleted(false);
        return user;
    }
}
