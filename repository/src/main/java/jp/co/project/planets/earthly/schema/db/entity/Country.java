package jp.co.project.planets.earthly.schema.db.entity;

import java.time.LocalDateTime;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Metamodel;
import org.seasar.doma.Table;

/**
 * 国
 */
@Entity(listener = CountryListener.class, metamodel = @Metamodel)
@Table(name = "country")
public class Country extends AbstractCountry implements java.io.Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @Column(name = "id")
    String id;

    /**
     * 名前
     */
    @Column(name = "name")
    String name;

    /**
     * リージョン
     */
    @Column(name = "region_id")
    String regionId;

    /**
     * 言語
     */
    @Column(name = "language_id")
    String languageId;

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

    public Country() {
    }

    /**
     * new instance
     *
     * @Param id id
     * @Param name 名前
     * @Param regionId リージョン
     * @Param languageId 言語
     * @Param createdAt 作成日
     * @Param createdBy 作成者
     * @Param updatedAt 更新日
     * @Param updatedBy 更新者
     * @Param isDeleted 削除フラグ
     */
    public Country(final String id, final String name, final String regionId, final String languageId,
            final LocalDateTime createdAt, final String createdBy, final LocalDateTime updatedAt,
            final String updatedBy, final Boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.regionId = regionId;
        this.languageId = languageId;
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
     * Returns the regionId.
     *
     * @return the regionId
     */
    public String getRegionId() {
        return regionId;
    }

    /**
     * Sets the regionId.
     *
     * @param regionId
     *            the regionId
     */
    public void setRegionId(final String regionId) {
        this.regionId = regionId;
    }

    /**
     * Returns the languageId.
     *
     * @return the languageId
     */
    public String getLanguageId() {
        return languageId;
    }

    /**
     * Sets the languageId.
     *
     * @param languageId
     *            the languageId
     */
    public void setLanguageId(final String languageId) {
        this.languageId = languageId;
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
