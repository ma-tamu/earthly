package jp.co.project.planets.earthly.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
        String language, String timezone, Boolean lockout, BelongCompanyEntity company, List<RoleSimpleEntity> roleList,
        LocalDateTime createdAt, OperationUser createdBy, LocalDateTime updatedAt, OperationUser updatedBy,
        Boolean isDeleted) implements Serializable {
}
