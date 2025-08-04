package jp.co.project.planets.earthly.webapp.security.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jp.co.project.planets.earthly.core.account.Account;

/**
 * ユーザー情報DTO
 *
 * @param account
 *            アカウント
 * @param password
 *            パスワード
 */
public record EarthlyUserInfoDto(Account account, String password,
        List<? extends GrantedAuthority> grantedAuthorities) implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return account.loginId();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !account.lockout();
    }

}
