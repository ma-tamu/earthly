package jp.co.project.planets.earthly.db.entity;

import java.time.LocalDateTime;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Metamodel;
import org.seasar.doma.Table;

/**
 * ユーザに紐づくロール
 */
@Entity(listener = UserRoleListener.class, metamodel = @Metamodel)
@Table(name = "user_role")
public class UserRole extends AbstractUserRole implements java.io.Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 1L;

    /** id */
    @Id
    @Column(name = "id")
    String id;

    /** id */
    @Column(name = "user_id")
    String userId;

    /** id */
    @Column(name = "role_id")
    String roleId;

    /** 作成日 */
    @Column(name = "created_at")
    LocalDateTime createdAt;

    /** 作成者 */
    @Column(name = "created_by")
    String createdBy;

    public UserRole() {
    }

    /**
     * new instance
     * 
     * @Param id id
     * @Param userId id
     * @Param roleId id
     * @Param createdAt 作成日
     * @Param createdBy 作成者
     */
    public UserRole(final String id, final String userId, final String roleId, final LocalDateTime createdAt,
            final String createdBy) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
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
     * Returns the roleId.
     * 
     * @return the roleId
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * Sets the roleId.
     * 
     * @param roleId
     *            the roleId
     */
    public void setRoleId(final String roleId) {
        this.roleId = roleId;
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
