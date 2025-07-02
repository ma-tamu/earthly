    package jp.co.project.planets.earthly.schema.db.entity;

    import java.time.LocalDateTime;
    import org.seasar.doma.Column;
    import org.seasar.doma.Entity;
    import org.seasar.doma.Id;
    import org.seasar.doma.Metamodel;
    import org.seasar.doma.Table;

/**
    * お知らせ
*/
@Entity(listener = NoticeListener.class, metamodel = @Metamodel)
@Table(name = "notice")
public class Notice extends AbstractNotice implements java.io.Serializable {

@java.io.Serial
private static final long serialVersionUID = 1L;


    /** お知らせID */
    @Id
    @Column(name = "id")
    String id;

    /** 件名 */
    @Column(name = "title")
    String title;

    /** 本文 */
    @Column(name = "body")
    String body;

    /** 掲載開始日 */
    @Column(name = "start_at")
    LocalDateTime startAt;

    /** 掲載終了日 */
    @Column(name = "end_at")
    LocalDateTime endAt;

    /** 強いお知らせか */
    @Column(name = "emphasis")
    Boolean emphasis;

    /** 作成日時 */
    @Column(name = "created_at")
    LocalDateTime createdAt;

    /** 作成者 */
    @Column(name = "created_by")
    String createdBy;

    /** 更新日時 */
    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    /** 更新者 */
    @Column(name = "updated_by")
    String updatedBy;

    /** 削除フラグ */
    @Column(name = "is_deleted")
    Boolean isDeleted;

public Notice() {
}
    /**
     * new instance
     * @param id
     *         お知らせID
     * @param title
     *         件名
     * @param body
     *         本文
     * @param startAt
     *         掲載開始日
     * @param endAt
     *         掲載終了日
     * @param emphasis
     *         強いお知らせか
     * @param createdAt
     *         作成日時
     * @param createdBy
     *         作成者
     * @param updatedAt
     *         更新日時
     * @param updatedBy
     *         更新者
     * @param isDeleted
     *         削除フラグ
     */
    public Notice(final String id,final String title,final String body,final LocalDateTime startAt,final LocalDateTime endAt,final Boolean emphasis,final LocalDateTime createdAt,final String createdBy,final LocalDateTime updatedAt,final String updatedBy,final Boolean isDeleted) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.startAt = startAt;
        this.endAt = endAt;
        this.emphasis = emphasis;
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
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the body.
     *
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets the body.
     *
     * @param body the body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Returns the startAt.
     *
     * @return the startAt
     */
    public LocalDateTime getStartAt() {
        return startAt;
    }

    /**
     * Sets the startAt.
     *
     * @param startAt the startAt
     */
    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }

    /**
     * Returns the endAt.
     *
     * @return the endAt
     */
    public LocalDateTime getEndAt() {
        return endAt;
    }

    /**
     * Sets the endAt.
     *
     * @param endAt the endAt
     */
    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
    }

    /**
     * Returns the emphasis.
     *
     * @return the emphasis
     */
    public Boolean getEmphasis() {
        return emphasis;
    }

    /**
     * Sets the emphasis.
     *
     * @param emphasis the emphasis
     */
    public void setEmphasis(Boolean emphasis) {
        this.emphasis = emphasis;
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
