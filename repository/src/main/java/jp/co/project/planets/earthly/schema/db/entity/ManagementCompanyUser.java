package jp.co.project.planets.earthly.schema.db.entity;

import java.time.LocalDateTime;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Metamodel;
import org.seasar.doma.Table;

/**
 * 会社管理
 */
@Entity(listener = ManagementCompanyUserListener.class, metamodel = @Metamodel)
@Table(name = "management_company_user")
public class ManagementCompanyUser extends AbstractManagementCompanyUser implements java.io.Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 1L;

    /**
     * 会社ID
     */
    @Column(name = "company_id")
    String companyId;

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

    /** 更新日 */
    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    /** 更新者 */
    @Column(name = "updated_by")
    String updatedBy;

    /** 削除フラグ */
    @Column(name = "is_deleted")
    Boolean isDeleted;

    /**  */
    @Id
    @Column(name = "id")
    String id;

    public ManagementCompanyUser() {
    }

    /**
     * new instance
     *
     * @Param companyId 会社ID
     * @Param userId ユーザーID
     * @Param createdAt 作成日
     * @Param createdBy 作成者
     * @Param updatedAt 更新日
     * @Param updatedBy 更新者
     * @Param isDeleted 削除フラグ
     * @Param id
     */
    public ManagementCompanyUser(final String companyId, final String userId, final LocalDateTime createdAt,
            final String createdBy, final LocalDateTime updatedAt, final String updatedBy, final Boolean isDeleted,
            final String id) {
        this.companyId = companyId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.isDeleted = isDeleted;
        this.id = id;
    }

    /**
     * Returns the companyId.
     *
     * @return the companyId
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * Sets the companyId.
     *
     * @param companyId
     *            the companyId
     */
    public void setCompanyId(final String companyId) {
        this.companyId = companyId;
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
}
