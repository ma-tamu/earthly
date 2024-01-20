package jp.co.project.planets.earthly.schema.db.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Metamodel;
import org.seasar.doma.Table;

/**
    * 
*/
@Entity(listener = RecoveryCodeListener.class, metamodel = @Metamodel)
@Table(name = "recovery_code")
public class RecoveryCode extends AbstractRecoveryCode implements java.io.Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 1L;

    /**  */
    @Id
    @Column(name = "id")
    String id;

    /**  */
    @Column(name = "userid")
    String userid;

    /**  */
    @Column(name = "code")
    String code;

    /**  */
    @Column(name = "used")
    Boolean used;

    public RecoveryCode() {
    }

    /**
     * new instance
     * 
     * @Param id
     * @Param userid
     * @Param code
     * @Param used
     */
    public RecoveryCode(final String id, final String userid, final String code, final Boolean used) {
        this.id = id;
        this.userid = userid;
        this.code = code;
        this.used = used;
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
     * Returns the userid.
     *
     * @return the userid
     */
    public String getUserid() {
        return userid;
    }

    /**
     * Sets the userid.
     *
     * @param userid
     *            the userid
     */
    public void setUserid(final String userid) {
        this.userid = userid;
    }

    /**
     * Returns the code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code.
     *
     * @param code
     *            the code
     */
    public void setCode(final String code) {
        this.code = code;
    }

    /**
     * Returns the used.
     *
     * @return the used
     */
    public Boolean getUsed() {
        return used;
    }

    /**
     * Sets the used.
     *
     * @param used
     *            the used
     */
    public void setUsed(final Boolean used) {
        this.used = used;
    }
}
