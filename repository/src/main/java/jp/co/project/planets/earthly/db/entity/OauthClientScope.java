package jp.co.project.planets.earthly.db.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Metamodel;
import org.seasar.doma.Table;

/**
 * OAuthクライアントスコープ
 */
@Entity(listener = OauthClientScopeListener.class, metamodel = @Metamodel)
@Table(name = "oauth_client_scope")
public class OauthClientScope extends AbstractOauthClientScope implements java.io.Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 1L;

    /** id */
    @Id
    @Column(name = "id")
    String id;

    /** OAuthクライアントid */
    @Column(name = "oauth_client_id")
    String oauthClientId;

    /** スコープid */
    @Column(name = "scope_id")
    String scopeId;

    public OauthClientScope() {
    }

    /**
     * new instance
     * 
     * @Param id id
     * @Param oauthClientId OAuthクライアントid
     * @Param scopeId スコープid
     */
    public OauthClientScope(final String id, final String oauthClientId, final String scopeId) {
        this.id = id;
        this.oauthClientId = oauthClientId;
        this.scopeId = scopeId;
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
     * Returns the oauthClientId.
     * 
     * @return the oauthClientId
     */
    public String getOauthClientId() {
        return oauthClientId;
    }

    /**
     * Sets the oauthClientId.
     * 
     * @param oauthClientId
     *            the oauthClientId
     */
    public void setOauthClientId(final String oauthClientId) {
        this.oauthClientId = oauthClientId;
    }

    /**
     * Returns the scopeId.
     * 
     * @return the scopeId
     */
    public String getScopeId() {
        return scopeId;
    }

    /**
     * Sets the scopeId.
     * 
     * @param scopeId
     *            the scopeId
     */
    public void setScopeId(final String scopeId) {
        this.scopeId = scopeId;
    }
}
