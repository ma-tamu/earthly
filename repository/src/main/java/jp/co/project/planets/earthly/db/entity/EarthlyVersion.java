    package jp.co.project.planets.earthly.db.entity;

    import java.time.LocalDateTime;
    import org.seasar.doma.Column;
    import org.seasar.doma.Entity;
    import org.seasar.doma.Id;
    import org.seasar.doma.Metamodel;
    import org.seasar.doma.Table;

/**
    * 
*/
@Entity(listener = EarthlyVersionListener.class )
    @Table(name = "earthly_version")
public class EarthlyVersion extends AbstractEarthlyVersion implements java.io.Serializable {

private static final long serialVersionUID = 1L;

        /**  */
        @Id
        @Column(name = "installed_rank")
    Integer installedRank;

        /**  */
        @Column(name = "version")
    String version;

        /**  */
        @Column(name = "description")
    String description;

        /**  */
        @Column(name = "type")
    String type;

        /**  */
        @Column(name = "script")
    String script;

        /**  */
        @Column(name = "checksum")
    Integer checksum;

        /**  */
        @Column(name = "installed_by")
    String installedBy;

        /**  */
        @Column(name = "installed_on")
    LocalDateTime installedOn;

        /**  */
        @Column(name = "execution_time")
    Integer executionTime;

        /**  */
        @Column(name = "success")
    Boolean success;

    public EarthlyVersion() {
    }
    /**
     * new instance
     * @Param installedRank
     *         
     * @Param version
     *         
     * @Param description
     *         
     * @Param type
     *         
     * @Param script
     *         
     * @Param checksum
     *         
     * @Param installedBy
     *         
     * @Param installedOn
     *         
     * @Param executionTime
     *         
     * @Param success
     *         
     */
    public EarthlyVersion(final Integer installedRank,final String version,final String description,final String type,final String script,final Integer checksum,final String installedBy,final LocalDateTime installedOn,final Integer executionTime,final Boolean success) {
        this.installedRank = installedRank;
        this.version = version;
        this.description = description;
        this.type = type;
        this.script = script;
        this.checksum = checksum;
        this.installedBy = installedBy;
        this.installedOn = installedOn;
        this.executionTime = executionTime;
        this.success = success;
    }

        /**
        * Returns the installedRank.
        *
        * @return the installedRank
        */
        public Integer getInstalledRank() {
        return installedRank;
        }

        /**
        * Sets the installedRank.
        *
        * @param installedRank the installedRank
        */
        public void setInstalledRank(Integer installedRank) {
        this.installedRank = installedRank;
        }

        /**
        * Returns the version.
        *
        * @return the version
        */
        public String getVersion() {
        return version;
        }

        /**
        * Sets the version.
        *
        * @param version the version
        */
        public void setVersion(String version) {
        this.version = version;
        }

        /**
        * Returns the description.
        *
        * @return the description
        */
        public String getDescription() {
        return description;
        }

        /**
        * Sets the description.
        *
        * @param description the description
        */
        public void setDescription(String description) {
        this.description = description;
        }

        /**
        * Returns the type.
        *
        * @return the type
        */
        public String getType() {
        return type;
        }

        /**
        * Sets the type.
        *
        * @param type the type
        */
        public void setType(String type) {
        this.type = type;
        }

        /**
        * Returns the script.
        *
        * @return the script
        */
        public String getScript() {
        return script;
        }

        /**
        * Sets the script.
        *
        * @param script the script
        */
        public void setScript(String script) {
        this.script = script;
        }

        /**
        * Returns the checksum.
        *
        * @return the checksum
        */
        public Integer getChecksum() {
        return checksum;
        }

        /**
        * Sets the checksum.
        *
        * @param checksum the checksum
        */
        public void setChecksum(Integer checksum) {
        this.checksum = checksum;
        }

        /**
        * Returns the installedBy.
        *
        * @return the installedBy
        */
        public String getInstalledBy() {
        return installedBy;
        }

        /**
        * Sets the installedBy.
        *
        * @param installedBy the installedBy
        */
        public void setInstalledBy(String installedBy) {
        this.installedBy = installedBy;
        }

        /**
        * Returns the installedOn.
        *
        * @return the installedOn
        */
        public LocalDateTime getInstalledOn() {
        return installedOn;
        }

        /**
        * Sets the installedOn.
        *
        * @param installedOn the installedOn
        */
        public void setInstalledOn(LocalDateTime installedOn) {
        this.installedOn = installedOn;
        }

        /**
        * Returns the executionTime.
        *
        * @return the executionTime
        */
        public Integer getExecutionTime() {
        return executionTime;
        }

        /**
        * Sets the executionTime.
        *
        * @param executionTime the executionTime
        */
        public void setExecutionTime(Integer executionTime) {
        this.executionTime = executionTime;
        }

        /**
        * Returns the success.
        *
        * @return the success
        */
        public Boolean getSuccess() {
        return success;
        }

        /**
        * Sets the success.
        *
        * @param success the success
        */
        public void setSuccess(Boolean success) {
        this.success = success;
        }
}
