package jp.co.project.planets.earthly.schema.model.entity;

import java.io.Serializable;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;

/**
 * OAuthクライアント管理ユーザー
 * 
 * @param id
 *            OAuthクライアント管理ID
 * @param oauthClientId
 *            OAuthクライアントID
 * @param userId
 *            ユーザーID
 * @param userName
 *            ユーザー名
 * @param companyId
 *            所属会社ID
 * @param companyName
 *            所属会社名
 */
@Entity
public record OAuthClientManagementUserEntity(String id, @Column(name = "oauth_client_id") String oauthClientId,
        @Column(name = "user_id") String userId, @Column(name = "user_name") String userName,
        @Column(name = "company_id") String companyId, @Column(name = "company_name") String companyName)
        implements Serializable {
}
