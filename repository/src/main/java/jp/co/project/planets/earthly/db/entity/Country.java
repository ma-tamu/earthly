package jp.co.project.planets.earthly.db.entity;

import java.time.LocalDateTime;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Metamodel;
import org.seasar.doma.Table;

/**
 * 国
 */
@Entity(listener = CountryListener.class )
@Table(name = "country")
public class Country extends AbstractCountry implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    @Id
    @Column(name = "id")
    String id;

    /** 名前 */
    @Column(name = "name")
    String name;

    /** リージョン */
    @Column(name = "region_id")
    String regionId;

    /** 言語 */
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
     * @param id the id
     */
    public void setId(String id) {
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
     * @param name the name
     */
    public void setName(String name) {
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
     * @param regionId the regionId
     */
    public void setRegionId(String regionId) {
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
     * @param languageId the languageId
     */
    public void setLanguageId(String languageId) {
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
     * @param createdAt the createdAt
     */
    public void setCreatedAt(LocalDateTime createdAt) {
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
     * @param createdBy the createdBy
     */
    public void setCreatedBy(String createdBy) {
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
     * @param updatedAt the updatedAt
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
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
     * @param updatedBy the updatedBy
     */
    public void setUpdatedBy(String updatedBy) {
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
     * @param isDeleted the isDeleted
     */
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
