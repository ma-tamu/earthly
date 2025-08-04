package jp.co.project.planets.earthly.webapp.security.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;

public final class SecurityContextUtils {

    private SecurityContextUtils() {
    }

    /**
     * update security context
     *
     * @param userInfoDto
     *            ユーザー情報
     */
    public static void updateSecurityContext(final EarthlyUserInfoDto userInfoDto) {

        final var earthlyUserInfoDto = new EarthlyUserInfoDto(userInfoDto.account(), userInfoDto.password(),
                userInfoDto.grantedAuthorities());
        final var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(earthlyUserInfoDto,
                userInfoDto.getPassword(), userInfoDto.getAuthorities());
        final var context = SecurityContextHolder.getContext();
        context.setAuthentication(usernamePasswordAuthenticationToken);
    }
}
