package jp.co.project.planets.earthly.webapp.config;

import jp.co.project.planets.earthly.webapp.view.dialect.CustomDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web mvc config
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public CustomDialect customDialect() {
        return new CustomDialect();
    }
}