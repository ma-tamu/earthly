package jp.co.project.planets.earthly.schema.db.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Metamodel;
import org.seasar.doma.Table;

/**
 * 作業時間
 */
@Entity(listener = WorkTimeListener.class, metamodel = @Metamodel)
@Table(name = "work_time")
public class WorkTime extends AbstractWorkTime implements java.io.Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @Column(name = "id")
    String id;

    /**
     * 年月日
     */
    @Column(name = "work_date")
    LocalDate workDate;

    /**
     * 始業時間
     */
    @Column(name = "start_time")
    LocalTime startTime;

    /** 終業時間 */
    @Column(name = "end_time")
    LocalTime endTime;

    /**
     * 所定内通常勤務
     */
    @Column(name = "official_working_time")
    LocalTime officialWorkingTime;

    /**
     * 所定内深夜勤務
     */
    @Column(name = "official_midnight_working_time")
    LocalTime officialMidnightWorkingTime;

    /** 所定内休憩勤務 */
    @Column(name = "official_break_time")
    LocalTime officialBreakTime;

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

    public WorkTime() {
    }

    /**
     * new instance
     *
     * @Param id id
     * @Param workDate 年月日
     * @Param startTime 始業時間
     * @Param endTime 終業時間
     * @Param officialWorkingTime 所定内通常勤務
     * @Param officialMidnightWorkingTime 所定内深夜勤務
     * @Param officialBreakTime 所定内休憩勤務
     * @Param createdAt 作成日
     * @Param createdBy 作成者
     * @Param updatedAt 更新日
     * @Param updatedBy 更新者
     * @Param isDeleted 削除フラグ
     */
    public WorkTime(final String id, final LocalDate workDate, final LocalTime startTime, final LocalTime endTime,
            final LocalTime officialWorkingTime, final LocalTime officialMidnightWorkingTime,
            final LocalTime officialBreakTime, final LocalDateTime createdAt, final String createdBy,
            final LocalDateTime updatedAt, final String updatedBy, final Boolean isDeleted) {
        this.id = id;
        this.workDate = workDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.officialWorkingTime = officialWorkingTime;
        this.officialMidnightWorkingTime = officialMidnightWorkingTime;
        this.officialBreakTime = officialBreakTime;
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
     * Returns the workDate.
     *
     * @return the workDate
     */
    public LocalDate getWorkDate() {
        return workDate;
    }

    /**
     * Sets the workDate.
     *
     * @param workDate
     *            the workDate
     */
    public void setWorkDate(final LocalDate workDate) {
        this.workDate = workDate;
    }

    /**
     * Returns the startTime.
     *
     * @return the startTime
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the startTime.
     *
     * @param startTime
     *            the startTime
     */
    public void setStartTime(final LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Returns the endTime.
     *
     * @return the endTime
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Sets the endTime.
     *
     * @param endTime
     *            the endTime
     */
    public void setEndTime(final LocalTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Returns the officialWorkingTime.
     *
     * @return the officialWorkingTime
     */
    public LocalTime getOfficialWorkingTime() {
        return officialWorkingTime;
    }

    /**
     * Sets the officialWorkingTime.
     *
     * @param officialWorkingTime
     *            the officialWorkingTime
     */
    public void setOfficialWorkingTime(final LocalTime officialWorkingTime) {
        this.officialWorkingTime = officialWorkingTime;
    }

    /**
     * Returns the officialMidnightWorkingTime.
     *
     * @return the officialMidnightWorkingTime
     */
    public LocalTime getOfficialMidnightWorkingTime() {
        return officialMidnightWorkingTime;
    }

    /**
     * Sets the officialMidnightWorkingTime.
     *
     * @param officialMidnightWorkingTime
     *            the officialMidnightWorkingTime
     */
    public void setOfficialMidnightWorkingTime(final LocalTime officialMidnightWorkingTime) {
        this.officialMidnightWorkingTime = officialMidnightWorkingTime;
    }

    /**
     * Returns the officialBreakTime.
     *
     * @return the officialBreakTime
     */
    public LocalTime getOfficialBreakTime() {
        return officialBreakTime;
    }

    /**
     * Sets the officialBreakTime.
     *
     * @param officialBreakTime
     *            the officialBreakTime
     */
    public void setOfficialBreakTime(final LocalTime officialBreakTime) {
        this.officialBreakTime = officialBreakTime;
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
