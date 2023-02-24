    package jp.co.project.planets.earthly.db.entity;

    import java.time.LocalDateTime;
    import org.seasar.doma.Column;
    import org.seasar.doma.Entity;
    import org.seasar.doma.Id;
    import org.seasar.doma.Metamodel;
    import org.seasar.doma.Table;

/**
    * ユーザー
*/
@Entity(listener = UserListener.class )
    @Table(name = "user")
public class User extends AbstractUser implements java.io.Serializable {

private static final long serialVersionUID = 1L;

        /** id */
        @Id
        @Column(name = "id")
    String id;

        /** ログインID */
        @Column(name = "login_id")
    String loginId;

        /** ユーザー名 */
        @Column(name = "name")
    String name;

        /** 性別 */
        @Column(name = "gender")
    String gender;

        /**  */
        @Column(name = "language")
    String language;

        /**  */
        @Column(name = "timezone")
    String timezone;

        /** メールアドレス */
        @Column(name = "mail")
    String mail;

        /** パスワード */
        @Column(name = "password")
    String password;

        /** ロックアウト */
        @Column(name = "lockout")
    Boolean lockout;

        /**  */
        @Column(name = "two_factor_authentication")
    Boolean twoFactorAuthentication;

        /**  */
        @Column(name = "secret")
    String secret;

        /** 所属会社 */
        @Column(name = "company_id")
    String companyId;

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

    public User() {
    }
    /**
     * new instance
     * @Param id
     *         id
     * @Param loginId
     *         ログインID
     * @Param name
     *         ユーザー名
     * @Param gender
     *         性別
     * @Param language
     *         
     * @Param timezone
     *         
     * @Param mail
     *         メールアドレス
     * @Param password
     *         パスワード
     * @Param lockout
     *         ロックアウト
     * @Param twoFactorAuthentication
     *         
     * @Param secret
     *         
     * @Param companyId
     *         所属会社
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
    public User(final String id,final String loginId,final String name,final String gender,final String language,final String timezone,final String mail,final String password,final Boolean lockout,final Boolean twoFactorAuthentication,final String secret,final String companyId,final LocalDateTime createdAt,final String createdBy,final LocalDateTime updatedAt,final String updatedBy,final Boolean isDeleted) {
        this.id = id;
        this.loginId = loginId;
        this.name = name;
        this.gender = gender;
        this.language = language;
        this.timezone = timezone;
        this.mail = mail;
        this.password = password;
        this.lockout = lockout;
        this.twoFactorAuthentication = twoFactorAuthentication;
        this.secret = secret;
        this.companyId = companyId;
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
        * Returns the loginId.
        *
        * @return the loginId
        */
        public String getLoginId() {
        return loginId;
        }

        /**
        * Sets the loginId.
        *
        * @param loginId the loginId
        */
        public void setLoginId(String loginId) {
        this.loginId = loginId;
        }

        /**
        * Returns the name.
        *
        * @return the name
        */
        public String getName() {
        return name;
        }

        /**
        * Sets the name.
        *
        * @param name the name
        */
        public void setName(String name) {
        this.name = name;
        }

        /**
        * Returns the gender.
        *
        * @return the gender
        */
        public String getGender() {
        return gender;
        }

        /**
        * Sets the gender.
        *
        * @param gender the gender
        */
        public void setGender(String gender) {
        this.gender = gender;
        }

        /**
        * Returns the language.
        *
        * @return the language
        */
        public String getLanguage() {
        return language;
        }

        /**
        * Sets the language.
        *
        * @param language the language
        */
        public void setLanguage(String language) {
        this.language = language;
        }

        /**
        * Returns the timezone.
        *
        * @return the timezone
        */
        public String getTimezone() {
        return timezone;
        }

        /**
        * Sets the timezone.
        *
        * @param timezone the timezone
        */
        public void setTimezone(String timezone) {
        this.timezone = timezone;
        }

        /**
        * Returns the mail.
        *
        * @return the mail
        */
        public String getMail() {
        return mail;
        }

        /**
        * Sets the mail.
        *
        * @param mail the mail
        */
        public void setMail(String mail) {
        this.mail = mail;
        }

        /**
        * Returns the password.
        *
        * @return the password
        */
        public String getPassword() {
        return password;
        }

        /**
        * Sets the password.
        *
        * @param password the password
        */
        public void setPassword(String password) {
        this.password = password;
        }

        /**
        * Returns the lockout.
        *
        * @return the lockout
        */
        public Boolean getLockout() {
        return lockout;
        }

        /**
        * Sets the lockout.
        *
        * @param lockout the lockout
        */
        public void setLockout(Boolean lockout) {
        this.lockout = lockout;
        }

        /**
        * Returns the twoFactorAuthentication.
        *
        * @return the twoFactorAuthentication
        */
        public Boolean getTwoFactorAuthentication() {
        return twoFactorAuthentication;
        }

        /**
        * Sets the twoFactorAuthentication.
        *
        * @param twoFactorAuthentication the twoFactorAuthentication
        */
        public void setTwoFactorAuthentication(Boolean twoFactorAuthentication) {
        this.twoFactorAuthentication = twoFactorAuthentication;
        }

        /**
        * Returns the secret.
        *
        * @return the secret
        */
        public String getSecret() {
        return secret;
        }

        /**
        * Sets the secret.
        *
        * @param secret the secret
        */
        public void setSecret(String secret) {
        this.secret = secret;
        }

        /**
        * Returns the companyId.
        *
        * @return the companyId
        */
        public String getCompanyId() {
        return companyId;
        }

        /**
        * Sets the companyId.
        *
        * @param companyId the companyId
        */
        public void setCompanyId(String companyId) {
        this.companyId = companyId;
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
