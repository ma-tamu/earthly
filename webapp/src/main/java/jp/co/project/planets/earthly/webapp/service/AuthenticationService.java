package jp.co.project.planets.earthly.webapp.service;

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
}
