    package jp.co.project.planets.earthly.db.entity;

    import java.time.LocalDateTime;
    import org.seasar.doma.Column;
    import org.seasar.doma.Entity;
    import org.seasar.doma.Id;
    import org.seasar.doma.Metamodel;
    import org.seasar.doma.Table;

/**
    * OAuthクライアントリダイレクトURL
*/
@Entity(listener = OauthClientRedirectUrlListener.class )
    @Table(name = "oauth_client_redirect_url")
public class OauthClientRedirectUrl extends AbstractOauthClientRedirectUrl implements java.io.Serializable {

private static final long serialVersionUID = 1L;

        /** id */
        @Id
        @Column(name = "id")
    String id;

        /** OAuthクライアントid */
        @Column(name = "oauth_client_id")
    String oauthClientId;

        /** リダイレクトURL */
        @Column(name = "redirect_url")
    String redirectUrl;

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

    public OauthClientRedirectUrl() {
    }
    /**
     * new instance
     * @Param id
     *         id
     * @Param oauthClientId
     *         OAuthクライアントid
     * @Param redirectUrl
     *         リダイレクトURL
     * @Param createdAt
     *         作成日
     * @Param createdBy
     *         作成者
     * @Param updatedAt
     *         更新日
     * @Param updatedBy
     *         更新者
     * @Param isDeleted
     *         削除フラグ
     */
    public OauthClientRedirectUrl(final String id,final String oauthClientId,final String redirectUrl,final LocalDateTime createdAt,final String createdBy,final LocalDateTime updatedAt,final String updatedBy,final Boolean isDeleted) {
        this.id = id;
        this.oauthClientId = oauthClientId;
        this.redirectUrl = redirectUrl;
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
        * @param oauthClientId the oauthClientId
        */
        public void setOauthClientId(String oauthClientId) {
        this.oauthClientId = oauthClientId;
        }

        /**
        * Returns the redirectUrl.
        *
        * @return the redirectUrl
        */
        public String getRedirectUrl() {
        return redirectUrl;
        }

        /**
        * Sets the redirectUrl.
        *
        * @param redirectUrl the redirectUrl
        */
        public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
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
