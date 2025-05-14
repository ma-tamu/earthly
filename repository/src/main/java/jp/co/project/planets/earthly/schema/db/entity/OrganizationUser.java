package jp.co.project.planets.earthly.schema.db.entity;

import java.time.LocalDateTime;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Metamodel;
import org.seasar.doma.Table;

/**
 * グループユーザー
 */
@Entity(listener = OrganizationUserListener.class, metamodel = @Metamodel)
@Table(name = "organization_user")
public class OrganizationUser extends AbstractOrganizationUser implements java.io.Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 1L;

    /** グループユーザーID */
    @Id
    @Column(name = "id")
    String id;

    /** グループID */
    @Column(name = "organization_id")
    String organizationId;

    /** ユーザーID */
    @Column(name = "user_id")
    String userId;

    /** 作成日 */
    @Column(name = "created_at")
    LocalDateTime createdAt;

    /** 作成者 */
    @Column(name = "created_by")
    String createdBy;

    public OrganizationUser() {
    }

    /**
     * new instance
     * 
     * @param id
     *            グループユーザーID
     * @param organizationId
     *            グループID
     * @param userId
     *            ユーザーID
     * @param createdAt
     *            作成日
     * @param createdBy
     *            作成者
     */
    public OrganizationUser(final String id, final String organizationId, final String userId,
        final LocalDateTime createdAt, final String createdBy) {
        this.id = id;
        this.organizationId = organizationId;
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
     * Returns the organizationId.
     *
     * @return the organizationId
     */
    public String getOrganizationId() {
        return organizationId;
    }

    /**
     * Sets the organizationId.
     *
     * @param organizationId
     *            the organizationId
     */
    public void setOrganizationId(final String organizationId) {
        this.organizationId = organizationId;
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
