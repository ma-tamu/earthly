package jp.co.project.planets.earthly.schema.model.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import org.seasar.doma.Association;
import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;

@Entity(naming = NamingType.SNAKE_LOWER_CASE)
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 5625028873040538675L;

    /** id */
    private String id;

    /** ログインID */
    private String loginId;

    /** ユーザー名 */
    private String name;

    /** 性別 */
    private String gender;

    /**  */
    private String language;

    /**  */
    private String timezone;

    /** メールアドレス */
    private String mail;

    /** パスワード */
    private String password;

    /** ロックアウト */
    private Boolean lockout;

    /**  */
    private Boolean twoFactorAuthentication;

    /**  */
    private String secret;

    /** 所属会社 */
    private String companyId;

    /** 作成日 */
    private LocalDateTime createdAt;

    /** 作成者 */
    private String createdBy;

    /** 更新日 */
    private LocalDateTime updatedAt;

    /** 更新者 */
    private String updatedBy;

    /** 削除フラグ */
    private Boolean isDeleted;

    @Association
    private Company company;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(final String loginId) {
        this.loginId = loginId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(final String timezone) {
        this.timezone = timezone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(final String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Boolean getLockout() {
        return lockout;
    }

    public void setLockout(final Boolean lockout) {
        this.lockout = lockout;
    }

    public Boolean getTwoFactorAuthentication() {
        return twoFactorAuthentication;
    }

    public void setTwoFactorAuthentication(final Boolean twoFactorAuthentication) {
        this.twoFactorAuthentication = twoFactorAuthentication;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(final String secret) {
        this.secret = secret;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(final String companyId) {
        this.companyId = companyId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(final Boolean deleted) {
        isDeleted = deleted;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(final Company company) {
        this.company = company;
    }
}
