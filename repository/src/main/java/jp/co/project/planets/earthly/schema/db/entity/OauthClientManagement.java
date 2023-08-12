package jp.co.project.planets.earthly.schema.db.entity;

import java.time.LocalDateTime;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Metamodel;
import org.seasar.doma.Table;

/**
 * OAuthクライアント管理者
 */
@Entity(listener = OauthClientManagementListener.class, metamodel = @Metamodel)
@Table(name = "oauth_client_management")
public class OauthClientManagement extends AbstractOauthClientManagement implements java.io.Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Id
    @Column(name = "id")
    String id;

    /**
     * OAuthクライアントId
     */
    @Column(name = "oauth_client_id")
    String oauthClientId;

    /**
     * ユーザーID
     */
    @Column(name = "user_id")
    String userId;

    /**
     * 作成日
     */
    @Column(name = "created_at")
    LocalDateTime createdAt;

    /**
     * 作成者
     */
    @Column(name = "created_by")
    String createdBy;

    public OauthClientManagement() {
    }

    /**
     * new instance
     *
     * @Param id ID
     * @Param oauthClientId OAuthクライアントId
     * @Param userId ユーザーID
     * @Param createdAt 作成日
     * @Param createdBy 作成者
     */
    public OauthClientManagement(final String id, final String oauthClientId, final String userId,
            final LocalDateTime createdAt, final String createdBy) {
        this.id = id;
        this.oauthClientId = oauthClientId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    /**
     * Returns the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the id
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Returns the oauthClientId.
     *
     * @return the oauthClientId
     */
    public String getOauthClientId() {
        return oauthClientId;
    }

    /**
     * Sets the oauthClientId.
     *
     * @param oauthClientId
     *            the oauthClientId
     */
    public void setOauthClientId(final String oauthClientId) {
        this.oauthClientId = oauthClientId;
    }

    /**
     * Returns the userId.
     *
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the userId.
     *
     * @param userId
     *            the userId
     */
    public void setUserId(final String userId) {
        this.userId = userId;
    }

    /**
     * Returns the createdAt.
     *
     * @return the createdAt
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the createdAt.
     *
     * @param createdAt
     *            the createdAt
     */
    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Returns the createdBy.
     *
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the createdBy.
     *
     * @param createdBy
     *            the createdBy
     */
    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }
}
