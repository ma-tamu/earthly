package jp.co.project.planets.earthly.webapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import jp.co.project.planets.earthly.webapp.security.mfa.MultiFactorAuthenticationSuccessHandler;
import jp.co.project.planets.earthly.webapp.security.mfa.MultiFactorAuthorizationManager;
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
        final AuthenticationSuccessHandler primarySuccessHandler) throws Exception {
        return httpSecurity.httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/login", "/forgets/**", "/css/**", "/js/**", "/img/**", "/static/**",
                                        "/vendor/**", "/quickTEST", "/error")
                                .permitAll().requestMatchers("/mfa").access(new MultiFactorAuthorizationManager())
                                .anyRequest().authenticated())
                .formLogin(formLoginConfigurer -> formLoginConfigurer.loginPage("/login").usernameParameter("loginId")
                        .passwordParameter("password").successHandler(primarySuccessHandler)
                        .successHandler(new MultiFactorAuthenticationSuccessHandler("/mfa", primarySuccessHandler)))
                .securityContext(context -> context.requireExplicitSave(false)).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler primarySuccessHandler() {
        return new SavedRequestAwareAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/login?error");
    }
}
