package jp.co.project.planets.earthly.auth.security.service;

import java.util.Locale;

import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;

import jp.co.project.planets.earthly.emuns.GenderEnum;
import jp.co.project.planets.earthly.repository.UserRepository;

@Service
public class OidcUserInfoService {

    private final UserRepository userRepository;

    public OidcUserInfoService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public OidcUserInfo loadUser(final String username) {

        final var userOptional = userRepository.findByLoginId(username);
        if (userOptional.isEmpty()) {
            return OidcUserInfo.builder().subject(username).build();
        }
        final var user = userOptional.get();
        return OidcUserInfo.builder() //
                .subject(user.getId()) //
                .name(user.getName()) //
                .preferredUsername(user.getLoginId()) //
                .email(user.getMail()) //
                .gender(GenderEnum.of(user.getGender()).name()) //
                .locale(Locale.forLanguageTag(user.getLanguage()).toString()) //
                .zoneinfo(user.getTimezone()) //
                .build();
    }
}
