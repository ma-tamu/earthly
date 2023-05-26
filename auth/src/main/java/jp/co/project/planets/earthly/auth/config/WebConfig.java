package jp.co.project.planets.earthly.auth.config;

import java.nio.charset.StandardCharsets;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jp.co.project.planets.earthly.auth.filter.RequestFilter;

/**
 * web config
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

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

    @Bean
    public FilterRegistrationBean<RequestFilter> requestFilterFilterRegistrationBean() {
        final var filterRegistrationBean = new FilterRegistrationBean<>(new RequestFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}
