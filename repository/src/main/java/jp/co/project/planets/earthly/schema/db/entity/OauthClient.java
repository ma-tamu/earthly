package jp.co.project.planets.earthly.schema.db.entity;

import java.time.LocalDateTime;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Metamodel;
import org.seasar.doma.Table;

/**
 * OAuthクライアント
 */
@Entity(listener = OauthClientListener.class, metamodel = @Metamodel)
@Table(name = "oauth_client")
public class OauthClient extends AbstractOauthClient implements java.io.Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @Column(name = "id")
    String id;

    /**
     * OAuthクライアント名
     */
    @Column(name = "name")
    String name;

    /**
     * クライアントID
     */
    @Column(name = "client_id")
    String clientId;

    /**
     * クライアントシークレット
     */
    @Column(name = "client_secret")
    String clientSecret;

    /** 作成日 */
    @Column(name = "created_at")
    LocalDateTime createdAt;

    /** 作成者 */
    @Column(name = "created_by")
    String createdBy;

    /** 更新日 */
    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    /** 更新者 */
    @Column(name = "updated_by")
    String updatedBy;

    /** 削除フラグ */
    @Column(name = "is_deleted")
    Boolean isDeleted;

    public OauthClient() {
    }

    /**
     * new instance
     *
     * @Param id id
     * @Param name OAuthクライアント名
     * @Param clientId クライアントID
     * @Param clientSecret クライアントシークレット
     * @Param createdAt 作成日
     * @Param createdBy 作成者
     * @Param updatedAt 更新日
     * @Param updatedBy 更新者
     * @Param isDeleted 削除フラグ
     */
    public OauthClient(final String id, final String name, final String clientId, final String clientSecret,
            final LocalDateTime createdAt, final String createdBy, final LocalDateTime updatedAt,
            final String updatedBy, final Boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.isDeleted = isDeleted;
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
     * Returns the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Returns the clientId.
     *
     * @return the clientId
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Sets the clientId.
     *
     * @param clientId
     *            the clientId
     */
    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }

    /**
     * Returns the clientSecret.
     *
     * @return the clientSecret
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * Sets the clientSecret.
     *
     * @param clientSecret
     *            the clientSecret
     */
    public void setClientSecret(final String clientSecret) {
        this.clientSecret = clientSecret;
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

    /**
     * Returns the updatedAt.
     *
     * @return the updatedAt
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the updatedAt.
     *
     * @param updatedAt
     *            the updatedAt
     */
    public void setUpdatedAt(final LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Returns the updatedBy.
     *
     * @return the updatedBy
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * Sets the updatedBy.
     *
     * @param updatedBy
     *            the updatedBy
     */
    public void setUpdatedBy(final String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * Returns the isDeleted.
     *
     * @return the isDeleted
     */
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    /**
     * Sets the isDeleted.
     *
     * @param isDeleted
     *            the isDeleted
     */
    public void setIsDeleted(final Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
