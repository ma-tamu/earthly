package jp.co.project.planets.earthly.webapp.security.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jp.co.project.planets.earthly.emuns.PermissionEnum;

/**
 * ユーザー情報DTO
 *
 * @param id
 *            ユーザーID
 * @param loginId
 *            ログインID
 * @param name
 *            ユーザー名
 * @param password
 *            パスワード
 * @param lockout
 *            ロックアウト
 * @param permissionEnumList
 *            パーミッションリスト
 */
public record EarthlyUserInfoDto(String id, String loginId, String name, String password, boolean lockout, boolean tfa,
        boolean tfaSuccessful, String secret, CompanyDto company, List<PermissionEnum> permissionEnumList,
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
        return name;
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
        return true;
    }
}
