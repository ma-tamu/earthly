package jp.co.project.planets.earthly.schema.db.entity;

import java.time.LocalDateTime;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Metamodel;
import org.seasar.doma.Table;

/**
 * ロールに紐づくパーミッション
 */
@Entity(listener = RolePermissionListener.class, metamodel = @Metamodel)
@Table(name = "role_permission")
public class RolePermission extends AbstractRolePermission implements java.io.Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @Column(name = "id")
    String id;

    /**
     * id
     */
    @Column(name = "role_id")
    String roleId;

    /**
     * id
     */
    @Column(name = "permission_id")
    String permissionId;

    /**
     * 作成日
     */
    @Column(name = "created_at")
    LocalDateTime createdAt;

    /** 作成者 */
    @Column(name = "created_by")
    String createdBy;

    public RolePermission() {
    }

    /**
     * new instance
     *
     * @Param id id
     * @Param roleId id
     * @Param permissionId id
     * @Param createdAt 作成日
     * @Param createdBy 作成者
     */
    public RolePermission(final String id, final String roleId, final String permissionId,
            final LocalDateTime createdAt, final String createdBy) {
        this.id = id;
        this.roleId = roleId;
        this.permissionId = permissionId;
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
     * Returns the permissionId.
     *
     * @return the permissionId
     */
    public String getPermissionId() {
        return permissionId;
    }

    /**
     * Sets the permissionId.
     *
     * @param permissionId
     *            the permissionId
     */
    public void setPermissionId(final String permissionId) {
        this.permissionId = permissionId;
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
