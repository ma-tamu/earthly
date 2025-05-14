package jp.co.project.planets.earthly.schema.model.entity;

import java.io.Serial;
import java.io.Serializable;

import org.seasar.doma.Association;
import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;

@Entity(naming = NamingType.SNAKE_LOWER_CASE)
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 5625028873040538675L;
    private String id;
    private String loginId;
    private String name;
    private String mail;
    private String gender;
    private String language;
    private String timezone;
    private Boolean lockout;
    private Boolean twoFactorAuthentication;
    @Association
    private Company company;

    public User() {
    }

    public User(final String id, final String loginId, final String name, final String mail, final String gender, final String language,
                final String timezone, final Boolean lockout, final Boolean twoFactorAuthentication, final Company company) {
        this.id = id;
        this.loginId = loginId;
        this.name = name;
        this.mail = mail;
        this.gender = gender;
        this.language = language;
        this.timezone = timezone;
        this.lockout = lockout;
        this.twoFactorAuthentication = twoFactorAuthentication;
        this.company = company;
    }

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

    public String getMail() {
        return mail;
    }

    public void setMail(final String mail) {
        this.mail = mail;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(final Company company) {
        this.company = company;
    }
}
