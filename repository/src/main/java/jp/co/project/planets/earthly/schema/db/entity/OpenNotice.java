package jp.co.project.planets.earthly.schema.db.entity;

import java.time.LocalDateTime;

import org.seasar.doma.*;

/**
 * 既読管理
 */
@Entity(listener = OpenNoticeListener.class, metamodel = @Metamodel)
@Table(name = "open_notice")
public class OpenNotice extends AbstractOpenNotice implements java.io.Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 1L;

    /** 既読管理ID */
    @Id
    @Column(name = "id")
    String id;

    /** ユーザーID */
    @Column(name = "user_id")
    String userId;

    /** 既読日時 */
    @Column(name = "opened_at")
    LocalDateTime openedAt;

    public OpenNotice() {
    }

    /**
     * new instance
     * 
     * @param id
     *            既読管理ID
     * @param userId
     *            ユーザーID
     * @param openedAt
     *            既読日時
     */
    public OpenNotice(final String id, final String userId, final LocalDateTime openedAt) {
        this.id = id;
        this.userId = userId;
        this.openedAt = openedAt;
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
     * Returns the openedAt.
     *
     * @return the openedAt
     */
    public LocalDateTime getOpenedAt() {
        return openedAt;
    }

    /**
     * Sets the openedAt.
     *
     * @param openedAt
     *            the openedAt
     */
    public void setOpenedAt(final LocalDateTime openedAt) {
        this.openedAt = openedAt;
    }
}
