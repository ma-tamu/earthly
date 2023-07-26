package jp.co.project.planets.earthly.webapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jp.co.project.planets.earthly.webapp.security.mfa.MfaAuthentication;
import jp.co.project.planets.earthly.webapp.security.mfa.MfaAuthenticationHandler;
import jp.co.project.planets.earthly.webapp.security.mfa.MfaFailureHandler;
import jp.co.project.planets.earthly.webapp.security.mfa.MfaSuccessHandler;
import jp.co.project.planets.earthly.webapp.security.mfa.MfaTrustResolver;
import jp.co.project.planets.earthly.webapp.security.service.DaoUserDetailService;

/**
 * web security config
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final DaoUserDetailService userDetailService;
    private final Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

    public WebSecurityConfig(final DaoUserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    /**
     * build security filter chain
     *
     * @param httpSecurity
     *            http security
     * @return SecurityFilterChain
     * @throws Exception
     *             security filter failed build
     */
    @Bean
    public SecurityFilterChain securityException(final HttpSecurity httpSecurity,
            final AuthorizationManager<RequestAuthorizationContext> mfaAuthorizationManager) throws Exception {
        final var mfaAuthenticationHandler = new MfaAuthenticationHandler();
        httpSecurity.httpBasic(AbstractHttpConfigurer::disable);
        httpSecurity
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/login", "/forgets/**", "/css/**", "/js/**", "/img/**", "/static/**",
                                        "/vendor/**", "/quickTEST", "/error")
                                .permitAll().requestMatchers("/mfa").access(mfaAuthorizationManager).anyRequest()
                                .authenticated())
                .formLogin(formLoginConfigurer -> formLoginConfigurer.loginPage("/login").usernameParameter("loginId")
                        .passwordParameter("password").successHandler(mfaAuthenticationHandler)
                        .failureHandler(mfaAuthenticationHandler))
                .exceptionHandling(exception -> exception
                        .withObjectPostProcessor(new ObjectPostProcessor<ExceptionTranslationFilter>() {
                            @Override
                            public <O extends ExceptionTranslationFilter> O postProcess(final O filter) {
                                filter.setAuthenticationTrustResolver(new MfaTrustResolver());
                                return filter;
                            }
                        }))
                .securityContext(context -> context.requireExplicitSave(false));
        return httpSecurity.build();
    }

    @Bean
    public AuthorizationManager<RequestAuthorizationContext> mfaAuthorizationManager() {
        return (authentication,
                context) -> new AuthorizationDecision(authentication.get() instanceof MfaAuthentication);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new MfaSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new MfaFailureHandler();
    }
}
