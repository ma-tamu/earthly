package jp.co.project.planets.earthly.webapp.config;

import jp.co.project.planets.earthly.webapp.view.dialect.CustomDialect;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;

/**
 * web mvc config
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * generate custom thymeleaf dialect
     *
     * @return dialect
     */
    @Bean
    public CustomDialect customDialect() {
        return new CustomDialect();
    }

    /**
     * generate message source
     *
     * @return message source
     */
    @Bean
    public MessageSource messageSource() {
        final var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        return messageSource;
    }
}