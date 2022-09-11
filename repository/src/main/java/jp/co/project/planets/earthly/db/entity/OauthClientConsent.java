    package jp.co.project.planets.earthly.db.entity;

    import org.seasar.doma.Column;
    import org.seasar.doma.Entity;
    import org.seasar.doma.Id;
    import org.seasar.doma.Metamodel;
    import org.seasar.doma.Table;

/**
    * OAuthクライアント承認
*/
@Entity(listener = OauthClientConsentListener.class )
    @Table(name = "oauth_client_consent")
public class OauthClientConsent extends AbstractOauthClientConsent implements java.io.Serializable {

private static final long serialVersionUID = 1L;

        /**  */
        @Id
        @Column(name = "registered_client_id")
    String registeredClientId;

        /**  */
        @Id
        @Column(name = "principal_name")
    String principalName;

        /**  */
        @Column(name = "authorities")
    String authorities;

    public OauthClientConsent() {
    }
    /**
     * new instance
     * @Param registeredClientId
     *         
     * @Param principalName
     *         
     * @Param authorities
     *         
     */
    public OauthClientConsent(final String registeredClientId,final String principalName,final String authorities) {
        this.registeredClientId = registeredClientId;
        this.principalName = principalName;
        this.authorities = authorities;
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
        * @param registeredClientId the registeredClientId
        */
        public void setRegisteredClientId(String registeredClientId) {
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
        * @param principalName the principalName
        */
        public void setPrincipalName(String principalName) {
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
        * @param authorities the authorities
        */
        public void setAuthorities(String authorities) {
        this.authorities = authorities;
        }
}
