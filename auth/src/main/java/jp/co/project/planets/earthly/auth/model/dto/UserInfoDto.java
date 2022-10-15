package jp.co.project.planets.earthly.auth.model.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * user info dto
 */
public class UserInfoDto implements UserDetails {

    private static final long serialVersionUID = 1L;

    /** user id */
    private final String id;
    /** login id */
    private final String loginId;
    /** password */
    private final String password;
    /** username */
    private final String name;
    /** lockout */
    private final boolean lockout;
    /** deleted */
    private final boolean deleted;

    /**
     * new instances user info dto
     *
     * @param id
     *         id
     * @param loginId
     *         login id
     * @param password
     *         password
     * @param name
     *         username
     * @param lockout
     *         lockout
     * @param deleted
     *         deleted
     */
    public UserInfoDto(final String id, final String loginId, final String password, final String name,
            final boolean lockout, final boolean deleted) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.lockout = lockout;
        this.deleted = deleted;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !lockout;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !deleted;
    }

    /**
     * getter id
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * getter login id
     *
     * @return login id
     */
    public String getLoginId() {
        return loginId;
    }

    /**
     * getter name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * getter lockout
     *
     * @return lockout
     */
    public boolean isLockout() {
        return lockout;
    }

    /**
     * getter deleted
     *
     * @return deleted
     */
    public boolean isDeleted() {
        return deleted;
    }
}
