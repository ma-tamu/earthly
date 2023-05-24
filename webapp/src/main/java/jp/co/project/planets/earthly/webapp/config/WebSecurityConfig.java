package jp.co.project.planets.earthly.webapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jp.co.project.planets.earthly.webapp.security.handler.LoginFailureHandler;
import jp.co.project.planets.earthly.webapp.security.handler.LoginSuccessHandler;
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
    public SecurityFilterChain securityException(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic(AbstractHttpConfigurer::disable);
        httpSecurity.securityMatcher(EndpointRequest.toAnyEndpoint())
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers("/login", "/css/**", "/js/**", "/img/**", "/static/**",
                                "/vendor/**", "/quickTEST").permitAll().anyRequest().authenticated())
                .formLogin(formLoginConfigurer -> formLoginConfigurer.loginPage("/login").usernameParameter("loginId")
                        .passwordParameter("password").successHandler(new LoginSuccessHandler())
                        .failureHandler(new LoginFailureHandler()));
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
