package jp.co.project.planets.earthly.webapp.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.time.SystemTimeProvider;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;

/**
 * authentication service
 */
@Service
public class AuthenticationService {

    /**
     * 2要素認証コードの検証
     *
     * @param code
     *            mfa code
     * @param userInfoDto
     *            ユーザー情報
     * @return true: 検証OK false: 検証失敗
     */
    public boolean verify(final String code, final EarthlyUserInfoDto userInfoDto) {

        final var systemTimeProvider = new SystemTimeProvider();
        final var defaultCodeGenerator = new DefaultCodeGenerator();
        final var codeVerifier = new DefaultCodeVerifier(defaultCodeGenerator, systemTimeProvider);
        return codeVerifier.isValidCode(userInfoDto.secret(), code);
    }

    /**
     * update security context
     * 
     * @param userInfoDto
     *            ユーザー情報
     */
    public void updateSecurityContext(final EarthlyUserInfoDto userInfoDto) {

        final var earthlyUserInfoDto = new EarthlyUserInfoDto(userInfoDto.id(), userInfoDto.loginId(),
                userInfoDto.name(), userInfoDto.password(), userInfoDto.lockout(), userInfoDto.tfa(), true,
                userInfoDto.secret(), userInfoDto.company(), userInfoDto.permissionEnumList(),
                userInfoDto.grantedAuthorities());
        final var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(earthlyUserInfoDto,
                userInfoDto.getPassword(), userInfoDto.getAuthorities());
        final var context = SecurityContextHolder.getContext();
        context.setAuthentication(usernamePasswordAuthenticationToken);
    }
}
