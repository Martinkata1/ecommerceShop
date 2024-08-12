package com.ecommerce.admin.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
/**
 * Configuration for Admin, it is meaning that the class
 * has defined beans and configuration, which is used from Spring IoC
 * container, its meaning configuration class
 */
@Configuration
/**
 * Activating support for web based security in Spring Security and
 * its allows to personalize the security
 */
@EnableWebSecurity
public class AdminConfiguration {

    /**
     * A Bean of type UserDetailsService is defined,
     * whichUserDetailsService is used by
     * Spring Security to load user details
     * (eg username, password, roles) upon authentication.
     * @return a new instance of AdminServiceConfig
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new AdminServiceConfig();
    }

    /**
     * A Bean of type BCryptPasswordEncoder is defined
     * that is used to hash the passwords. Best way
     * to use for secure stored passwords
     * @return a new instance of BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * This method configures the security of HTTP requests.
     * Some of the basic settings include:
     * Authentication and Authorization:
     * It specifies which URL paths are protected and which are available to everyone.
     * ->/admin/** requires the user to have the ADMIN role.
     * ->/forgot-password, ->/register, and ->/register-new are publicly available.
     * Entry form:
     * Users are redirected to ->/login for authentication,
     * and if successful authentication occurs, they will be redirected to /index.
     * Logout:
     * Sets a logout URL path ->(/logout) that clears the session and authentication.
     * Session:
     * Creates a new session for each new user.
     * Disable CSRF: Cross-Site Request Forgery
     * CSRF protection is turned off, which may be appropriate for APIs or other specific cases, but requires careful use.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder
                = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(author ->
                        author.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                                .requestMatchers("/forgot-password", "/register", "/register-new").permitAll()
                                .anyRequest().authenticated()

                )
                .formLogin(login ->
                        login.loginPage("/login")
                                .loginProcessingUrl("/do-login")
                                .defaultSuccessUrl("/index", true)
                                .permitAll()
                )
                .logout(logout ->
                        logout.invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/login?logout")
                                .permitAll()
                )
                .authenticationManager(authenticationManager)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                )
        ;
        return http.build();
    }

}
