package jp.co.project.planets.earthly.auth.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.project.planets.moon.jackson.UserInfoDtoMixin;
import jp.co.project.planets.moon.model.dto.UserInfoDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.jackson2.CoreJackson2Module;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;

/**
 * jackson config
 */
@Configuration
public class JacksonConfig {

    /**
     * ObjectMapperを生成
     *
     * @return ObjectMapper
     */
    @Bean
    public ObjectMapper objectMapper() {
        final var classLoader = JdbcOAuth2AuthorizationService.class.getClassLoader();
        final var securityModules = SecurityJackson2Modules.getModules(classLoader);

        return new ObjectMapper()
                .registerModules(securityModules)
                .registerModule(new OAuth2AuthorizationServerJackson2Module())
                .registerModule(new CoreJackson2Module())
                .addMixIn(UserInfoDto.class, UserInfoDtoMixin.class)
                .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

}
