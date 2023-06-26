package jp.co.project.planets.earthly.schema.model.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;

/**
 * user simple entity
 *
 * @param id
 *            ユーザーID
 * @param loginId
 *            ログインID
 * @param name
 *            ユーザー名
 * @param mail
 *            メールアドレス
 * @param gender
 *            性別
 * @param companyId
 *            所属会社ID
 * @param companyName
 *            所属会社名
 */
@Entity(naming = NamingType.SNAKE_LOWER_CASE)
public record UserSimpleEntity(String id, String loginId, String name, String mail, String gender, Boolean lockout,
        String companyId, String companyName) {
}
