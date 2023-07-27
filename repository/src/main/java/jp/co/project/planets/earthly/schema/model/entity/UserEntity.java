package jp.co.project.planets.earthly.schema.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import jp.co.project.planets.earthly.schema.db.entity.Role;

/**
 * user entity
 *
 * @param id
 *            ユーザーID
 * @param loginId
 *            ログインID
 * @param name
 *            ユーザー名
 * @param gender
 *            性別
 * @param mail
 *            メールアドレス
 * @param password
 *            パスワード
 * @param language
 *            言語
 * @param timezone
 *            タイムゾーン
 * @param lockout
 *            ロックアウト
 * @param isMfa
 *            多要素認証の有無
 * @param company
 *            所属会社
 * @param roleList
 *            保持ロールリスト
 * @param createdAt
 *            作成日時
 * @param createdBy
 *            作成者
 * @param updatedAt
 *            更新日時
 * @param updatedBy
 *            更新者
 * @param isDeleted
 *            削除フラグ
 */
public record UserEntity(String id, String loginId, String name, String gender, String mail, String password,
        String language, String timezone, Boolean lockout, Boolean isMfa, String secret,
        BelongCompanyEntity company,
        List<Role> roleList, List<CompanySimpleEntity> managementCompanyList, LocalDateTime createdAt,
        OperationUser createdBy, LocalDateTime updatedAt, OperationUser updatedBy, Boolean isDeleted)
        implements Serializable {
}
