package jp.co.project.planets.earthly.schema.db.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Metamodel;
import org.seasar.doma.Table;

/**
 * OAuthクライアント認可タイプ
 */
@Entity(listener = OauthClientGrantTypeListener.class, metamodel = @Metamodel)
@Table(name = "oauth_client_grant_type")
public class OauthClientGrantType extends AbstractOauthClientGrantType implements java.io.Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @Column(name = "id")
    String id;

    /**
     * OAuthクライアントid
     */
    @Column(name = "oauth_client_id")
    String oauthClientId;

    /**
     * 認可タイプid
     */
    @Column(name = "grant_type_id")
    String grantTypeId;

    public OauthClientGrantType() {
    }

    /**
     * new instance
     *
     * @Param id id
     * @Param oauthClientId OAuthクライアントid
     * @Param grantTypeId 認可タイプid
     */
    public OauthClientGrantType(final String id, final String oauthClientId, final String grantTypeId) {
        this.id = id;
        this.oauthClientId = oauthClientId;
        this.grantTypeId = grantTypeId;
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
     * Returns the grantTypeId.
     *
     * @return the grantTypeId
     */
    public String getGrantTypeId() {
        return grantTypeId;
    }

    /**
     * Sets the grantTypeId.
     *
     * @param grantTypeId
     *            the grantTypeId
     */
    public void setGrantTypeId(final String grantTypeId) {
        this.grantTypeId = grantTypeId;
    }
}
