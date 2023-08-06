package jp.co.project.planets.earthly.schema.db.entity;

import java.time.LocalDateTime;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Metamodel;
import org.seasar.doma.Table;

/**
    * 
*/
@Entity(listener = Oauth2AuthorizationListener.class, metamodel = @Metamodel)
@Table(name = "oauth2_authorization")
public class Oauth2Authorization extends AbstractOauth2Authorization implements java.io.Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @Id
    @Column(name = "id")
    String id;

    /**
     *
     */
    @Column(name = "registered_client_id")
    String registeredClientId;

    /**
     *
     */
    @Column(name = "principal_name")
    String principalName;

    /**
     *
     */
    @Column(name = "authorization_grant_type")
    String authorizationGrantType;

    /**  */
    @Column(name = "attributes")
    String attributes;

    /**  */
    @Column(name = "state")
    String state;

    /**
     *
     */
    @Column(name = "authorization_code_value")
    String authorizationCodeValue;

    /**
     *
     */
    @Column(name = "authorization_code_issued_at")
    LocalDateTime authorizationCodeIssuedAt;

    /**  */
    @Column(name = "authorization_code_expires_at")
    LocalDateTime authorizationCodeExpiresAt;

    /**  */
    @Column(name = "authorization_code_metadata")
    String authorizationCodeMetadata;

    /**  */
    @Column(name = "access_token_value")
    String accessTokenValue;

    /**  */
    @Column(name = "access_token_issued_at")
    LocalDateTime accessTokenIssuedAt;

    /**  */
    @Column(name = "access_token_expires_at")
    LocalDateTime accessTokenExpiresAt;

    /**  */
    @Column(name = "access_token_metadata")
    String accessTokenMetadata;

    /**  */
    @Column(name = "access_token_type")
    String accessTokenType;

    /**  */
    @Column(name = "access_token_scopes")
    String accessTokenScopes;

    /**  */
    @Column(name = "oidc_id_token_value")
    String oidcIdTokenValue;

    /**  */
    @Column(name = "oidc_id_token_issued_at")
    LocalDateTime oidcIdTokenIssuedAt;

    /**  */
    @Column(name = "oidc_id_token_expires_at")
    LocalDateTime oidcIdTokenExpiresAt;

    /**  */
    @Column(name = "oidc_id_token_metadata")
    String oidcIdTokenMetadata;

    /**  */
    @Column(name = "refresh_token_value")
    String refreshTokenValue;

    /**  */
    @Column(name = "refresh_token_issued_at")
    LocalDateTime refreshTokenIssuedAt;

    /**  */
    @Column(name = "refresh_token_expires_at")
    LocalDateTime refreshTokenExpiresAt;

    /**  */
    @Column(name = "refresh_token_metadata")
    String refreshTokenMetadata;

    public Oauth2Authorization() {
    }

    /**
     * new instance
     *
     * @Param id
     * @Param registeredClientId
     * @Param principalName
     * @Param authorizationGrantType
     * @Param attributes
     * @Param state
     * @Param authorizationCodeValue
     * @Param authorizationCodeIssuedAt
     * @Param authorizationCodeExpiresAt
     * @Param authorizationCodeMetadata
     * @Param accessTokenValue
     * @Param accessTokenIssuedAt
     * @Param accessTokenExpiresAt
     * @Param accessTokenMetadata
     * @Param accessTokenType
     * @Param accessTokenScopes
     * @Param oidcIdTokenValue
     * @Param oidcIdTokenIssuedAt
     * @Param oidcIdTokenExpiresAt
     * @Param oidcIdTokenMetadata
     * @Param refreshTokenValue
     * @Param refreshTokenIssuedAt
     * @Param refreshTokenExpiresAt
     * @Param refreshTokenMetadata
     */
    public Oauth2Authorization(final String id, final String registeredClientId, final String principalName,
            final String authorizationGrantType, final String attributes, final String state,
            final String authorizationCodeValue, final LocalDateTime authorizationCodeIssuedAt,
            final LocalDateTime authorizationCodeExpiresAt, final String authorizationCodeMetadata,
            final String accessTokenValue, final LocalDateTime accessTokenIssuedAt,
            final LocalDateTime accessTokenExpiresAt, final String accessTokenMetadata, final String accessTokenType,
            final String accessTokenScopes, final String oidcIdTokenValue, final LocalDateTime oidcIdTokenIssuedAt,
            final LocalDateTime oidcIdTokenExpiresAt, final String oidcIdTokenMetadata, final String refreshTokenValue,
            final LocalDateTime refreshTokenIssuedAt, final LocalDateTime refreshTokenExpiresAt,
            final String refreshTokenMetadata) {
        this.id = id;
        this.registeredClientId = registeredClientId;
        this.principalName = principalName;
        this.authorizationGrantType = authorizationGrantType;
        this.attributes = attributes;
        this.state = state;
        this.authorizationCodeValue = authorizationCodeValue;
        this.authorizationCodeIssuedAt = authorizationCodeIssuedAt;
        this.authorizationCodeExpiresAt = authorizationCodeExpiresAt;
        this.authorizationCodeMetadata = authorizationCodeMetadata;
        this.accessTokenValue = accessTokenValue;
        this.accessTokenIssuedAt = accessTokenIssuedAt;
        this.accessTokenExpiresAt = accessTokenExpiresAt;
        this.accessTokenMetadata = accessTokenMetadata;
        this.accessTokenType = accessTokenType;
        this.accessTokenScopes = accessTokenScopes;
        this.oidcIdTokenValue = oidcIdTokenValue;
        this.oidcIdTokenIssuedAt = oidcIdTokenIssuedAt;
        this.oidcIdTokenExpiresAt = oidcIdTokenExpiresAt;
        this.oidcIdTokenMetadata = oidcIdTokenMetadata;
        this.refreshTokenValue = refreshTokenValue;
        this.refreshTokenIssuedAt = refreshTokenIssuedAt;
        this.refreshTokenExpiresAt = refreshTokenExpiresAt;
        this.refreshTokenMetadata = refreshTokenMetadata;
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
     * Returns the authorizationGrantType.
     *
     * @return the authorizationGrantType
     */
    public String getAuthorizationGrantType() {
        return authorizationGrantType;
    }

    /**
     * Sets the authorizationGrantType.
     *
     * @param authorizationGrantType
     *            the authorizationGrantType
     */
    public void setAuthorizationGrantType(final String authorizationGrantType) {
        this.authorizationGrantType = authorizationGrantType;
    }

    /**
     * Returns the attributes.
     *
     * @return the attributes
     */
    public String getAttributes() {
        return attributes;
    }

    /**
     * Sets the attributes.
     *
     * @param attributes
     *            the attributes
     */
    public void setAttributes(final String attributes) {
        this.attributes = attributes;
    }

    /**
     * Returns the state.
     *
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state.
     *
     * @param state
     *            the state
     */
    public void setState(final String state) {
        this.state = state;
    }

    /**
     * Returns the authorizationCodeValue.
     *
     * @return the authorizationCodeValue
     */
    public String getAuthorizationCodeValue() {
        return authorizationCodeValue;
    }

    /**
     * Sets the authorizationCodeValue.
     *
     * @param authorizationCodeValue
     *            the authorizationCodeValue
     */
    public void setAuthorizationCodeValue(final String authorizationCodeValue) {
        this.authorizationCodeValue = authorizationCodeValue;
    }

    /**
     * Returns the authorizationCodeIssuedAt.
     *
     * @return the authorizationCodeIssuedAt
     */
    public LocalDateTime getAuthorizationCodeIssuedAt() {
        return authorizationCodeIssuedAt;
    }

    /**
     * Sets the authorizationCodeIssuedAt.
     *
     * @param authorizationCodeIssuedAt
     *            the authorizationCodeIssuedAt
     */
    public void setAuthorizationCodeIssuedAt(final LocalDateTime authorizationCodeIssuedAt) {
        this.authorizationCodeIssuedAt = authorizationCodeIssuedAt;
    }

    /**
     * Returns the authorizationCodeExpiresAt.
     *
     * @return the authorizationCodeExpiresAt
     */
    public LocalDateTime getAuthorizationCodeExpiresAt() {
        return authorizationCodeExpiresAt;
    }

    /**
     * Sets the authorizationCodeExpiresAt.
     *
     * @param authorizationCodeExpiresAt
     *            the authorizationCodeExpiresAt
     */
    public void setAuthorizationCodeExpiresAt(final LocalDateTime authorizationCodeExpiresAt) {
        this.authorizationCodeExpiresAt = authorizationCodeExpiresAt;
    }

    /**
     * Returns the authorizationCodeMetadata.
     *
     * @return the authorizationCodeMetadata
     */
    public String getAuthorizationCodeMetadata() {
        return authorizationCodeMetadata;
    }

    /**
     * Sets the authorizationCodeMetadata.
     *
     * @param authorizationCodeMetadata
     *            the authorizationCodeMetadata
     */
    public void setAuthorizationCodeMetadata(final String authorizationCodeMetadata) {
        this.authorizationCodeMetadata = authorizationCodeMetadata;
    }

    /**
     * Returns the accessTokenValue.
     *
     * @return the accessTokenValue
     */
    public String getAccessTokenValue() {
        return accessTokenValue;
    }

    /**
     * Sets the accessTokenValue.
     *
     * @param accessTokenValue
     *            the accessTokenValue
     */
    public void setAccessTokenValue(final String accessTokenValue) {
        this.accessTokenValue = accessTokenValue;
    }

    /**
     * Returns the accessTokenIssuedAt.
     *
     * @return the accessTokenIssuedAt
     */
    public LocalDateTime getAccessTokenIssuedAt() {
        return accessTokenIssuedAt;
    }

    /**
     * Sets the accessTokenIssuedAt.
     *
     * @param accessTokenIssuedAt
     *            the accessTokenIssuedAt
     */
    public void setAccessTokenIssuedAt(final LocalDateTime accessTokenIssuedAt) {
        this.accessTokenIssuedAt = accessTokenIssuedAt;
    }

    /**
     * Returns the accessTokenExpiresAt.
     *
     * @return the accessTokenExpiresAt
     */
    public LocalDateTime getAccessTokenExpiresAt() {
        return accessTokenExpiresAt;
    }

    /**
     * Sets the accessTokenExpiresAt.
     *
     * @param accessTokenExpiresAt
     *            the accessTokenExpiresAt
     */
    public void setAccessTokenExpiresAt(final LocalDateTime accessTokenExpiresAt) {
        this.accessTokenExpiresAt = accessTokenExpiresAt;
    }

    /**
     * Returns the accessTokenMetadata.
     *
     * @return the accessTokenMetadata
     */
    public String getAccessTokenMetadata() {
        return accessTokenMetadata;
    }

    /**
     * Sets the accessTokenMetadata.
     *
     * @param accessTokenMetadata
     *            the accessTokenMetadata
     */
    public void setAccessTokenMetadata(final String accessTokenMetadata) {
        this.accessTokenMetadata = accessTokenMetadata;
    }

    /**
     * Returns the accessTokenType.
     *
     * @return the accessTokenType
     */
    public String getAccessTokenType() {
        return accessTokenType;
    }

    /**
     * Sets the accessTokenType.
     *
     * @param accessTokenType
     *            the accessTokenType
     */
    public void setAccessTokenType(final String accessTokenType) {
        this.accessTokenType = accessTokenType;
    }

    /**
     * Returns the accessTokenScopes.
     *
     * @return the accessTokenScopes
     */
    public String getAccessTokenScopes() {
        return accessTokenScopes;
    }

    /**
     * Sets the accessTokenScopes.
     *
     * @param accessTokenScopes
     *            the accessTokenScopes
     */
    public void setAccessTokenScopes(final String accessTokenScopes) {
        this.accessTokenScopes = accessTokenScopes;
    }

    /**
     * Returns the oidcIdTokenValue.
     *
     * @return the oidcIdTokenValue
     */
    public String getOidcIdTokenValue() {
        return oidcIdTokenValue;
    }

    /**
     * Sets the oidcIdTokenValue.
     *
     * @param oidcIdTokenValue
     *            the oidcIdTokenValue
     */
    public void setOidcIdTokenValue(final String oidcIdTokenValue) {
        this.oidcIdTokenValue = oidcIdTokenValue;
    }

    /**
     * Returns the oidcIdTokenIssuedAt.
     *
     * @return the oidcIdTokenIssuedAt
     */
    public LocalDateTime getOidcIdTokenIssuedAt() {
        return oidcIdTokenIssuedAt;
    }

    /**
     * Sets the oidcIdTokenIssuedAt.
     *
     * @param oidcIdTokenIssuedAt
     *            the oidcIdTokenIssuedAt
     */
    public void setOidcIdTokenIssuedAt(final LocalDateTime oidcIdTokenIssuedAt) {
        this.oidcIdTokenIssuedAt = oidcIdTokenIssuedAt;
    }

    /**
     * Returns the oidcIdTokenExpiresAt.
     *
     * @return the oidcIdTokenExpiresAt
     */
    public LocalDateTime getOidcIdTokenExpiresAt() {
        return oidcIdTokenExpiresAt;
    }

    /**
     * Sets the oidcIdTokenExpiresAt.
     *
     * @param oidcIdTokenExpiresAt
     *            the oidcIdTokenExpiresAt
     */
    public void setOidcIdTokenExpiresAt(final LocalDateTime oidcIdTokenExpiresAt) {
        this.oidcIdTokenExpiresAt = oidcIdTokenExpiresAt;
    }

    /**
     * Returns the oidcIdTokenMetadata.
     *
     * @return the oidcIdTokenMetadata
     */
    public String getOidcIdTokenMetadata() {
        return oidcIdTokenMetadata;
    }

    /**
     * Sets the oidcIdTokenMetadata.
     *
     * @param oidcIdTokenMetadata
     *            the oidcIdTokenMetadata
     */
    public void setOidcIdTokenMetadata(final String oidcIdTokenMetadata) {
        this.oidcIdTokenMetadata = oidcIdTokenMetadata;
    }

    /**
     * Returns the refreshTokenValue.
     *
     * @return the refreshTokenValue
     */
    public String getRefreshTokenValue() {
        return refreshTokenValue;
    }

    /**
     * Sets the refreshTokenValue.
     *
     * @param refreshTokenValue
     *            the refreshTokenValue
     */
    public void setRefreshTokenValue(final String refreshTokenValue) {
        this.refreshTokenValue = refreshTokenValue;
    }

    /**
     * Returns the refreshTokenIssuedAt.
     *
     * @return the refreshTokenIssuedAt
     */
    public LocalDateTime getRefreshTokenIssuedAt() {
        return refreshTokenIssuedAt;
    }

    /**
     * Sets the refreshTokenIssuedAt.
     *
     * @param refreshTokenIssuedAt
     *            the refreshTokenIssuedAt
     */
    public void setRefreshTokenIssuedAt(final LocalDateTime refreshTokenIssuedAt) {
        this.refreshTokenIssuedAt = refreshTokenIssuedAt;
    }

    /**
     * Returns the refreshTokenExpiresAt.
     *
     * @return the refreshTokenExpiresAt
     */
    public LocalDateTime getRefreshTokenExpiresAt() {
        return refreshTokenExpiresAt;
    }

    /**
     * Sets the refreshTokenExpiresAt.
     *
     * @param refreshTokenExpiresAt
     *            the refreshTokenExpiresAt
     */
    public void setRefreshTokenExpiresAt(final LocalDateTime refreshTokenExpiresAt) {
        this.refreshTokenExpiresAt = refreshTokenExpiresAt;
    }

    /**
     * Returns the refreshTokenMetadata.
     *
     * @return the refreshTokenMetadata
     */
    public String getRefreshTokenMetadata() {
        return refreshTokenMetadata;
    }

    /**
     * Sets the refreshTokenMetadata.
     *
     * @param refreshTokenMetadata
     *            the refreshTokenMetadata
     */
    public void setRefreshTokenMetadata(final String refreshTokenMetadata) {
        this.refreshTokenMetadata = refreshTokenMetadata;
    }
}
