package jp.co.project.planets.earthly.webapp.config;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Locale;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import jp.co.project.planets.earthly.webapp.filter.RequestFilter;
import jp.co.project.planets.earthly.webapp.view.dialect.CustomDialect;

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

    /**
     * generate filter registration bean
     * 
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean<RequestFilter> requestFilterFilterRegistrationBean() {
        final var filterRegistrationBean = new FilterRegistrationBean<>(new RequestFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    @Bean
    public LocaleResolver localeResolver() {
        final var localeResolver = new CookieLocaleResolver("lang");
        localeResolver.setDefaultLocale(Locale.JAPANESE);
        localeResolver.setCookieHttpOnly(true);
        localeResolver.setCookieSecure(true);
        localeResolver.setCookieMaxAge(Duration.ofSeconds(2147483647));
        return localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        final var localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}