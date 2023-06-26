package jp.co.project.planets.earthly.schema.db.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Metamodel;
import org.seasar.doma.Table;

/**
    * 
*/
@Entity(listener = Oauth2AuthorizationConsentListener.class, metamodel = @Metamodel)
@Table(name = "oauth2_authorization_consent")
public class Oauth2AuthorizationConsent extends AbstractOauth2AuthorizationConsent implements java.io.Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 1L;

    /**  */
    @Id
    @Column(name = "id")
    String id;

    /**  */
    @Column(name = "registered_client_id")
    String registeredClientId;

    /**  */
    @Column(name = "principal_name")
    String principalName;

    /**  */
    @Column(name = "authorities")
    String authorities;

    public Oauth2AuthorizationConsent() {
    }

    /**
     * new instance
     * 
     * @Param id
     * @Param registeredClientId
     * @Param principalName
     * @Param authorities
     */
    public Oauth2AuthorizationConsent(final String id, final String registeredClientId, final String principalName,
            final String authorities) {
        this.id = id;
        this.registeredClientId = registeredClientId;
        this.principalName = principalName;
        this.authorities = authorities;
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
     * Returns the registeredClientId.
     *
     * @return the registeredClientId
     */
    public String getRegisteredClientId() {
        return registeredClientId;
    }

    /**
     * Sets the registeredClientId.
     *
     * @param registeredClientId
     *            the registeredClientId
     */
    public void setRegisteredClientId(final String registeredClientId) {
        this.registeredClientId = registeredClientId;
    }

    /**
     * Returns the principalName.
     *
     * @return the principalName
     */
    public String getPrincipalName() {
        return principalName;
    }

    /**
     * Sets the principalName.
     *
     * @param principalName
     *            the principalName
     */
    public void setPrincipalName(final String principalName) {
        this.principalName = principalName;
    }

    /**
     * Returns the authorities.
     *
     * @return the authorities
     */
    public String getAuthorities() {
        return authorities;
    }

    /**
     * Sets the authorities.
     *
     * @param authorities
     *            the authorities
     */
    public void setAuthorities(final String authorities) {
        this.authorities = authorities;
    }
}
