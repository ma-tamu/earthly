package jp.co.project.planets.earthly.auth.config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * redis config
 */
@Profile("!local & !test")
@Configuration
@EnableRedisHttpSession
public class RedisConfig {

    /**
     * generate redis connection factory
     * 
     * @param redisProperties
     *            redis properties
     * @return LettuceConnectionFactory
     */
    @Bean
    public LettuceConnectionFactory redisConnectionFactory(final RedisProperties redisProperties) {
        final var configuration = new RedisStandaloneConfiguration(redisProperties.getHost(),
                redisProperties.getPort());
        return new LettuceConnectionFactory(configuration);
    }

    /**
     * redis configure action
     * 
     * @return ConfigureRedisAction
     */
    @Bean
    public ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }
}
