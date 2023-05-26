package jp.co.project.planets.earthly.auth.config;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

/**
 * session config
 */
@Profile("local | test")
@Configuration
@EnableSpringHttpSession
public class SessionConfig {

    /**
     * generate session repository
     * 
     * @return MapSessionRepository
     */
    @Bean
    public MapSessionRepository sessionRepository() {
        return new MapSessionRepository(new ConcurrentHashMap<>());
    }
}
