package com.ecommerce.customer.config;

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
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * This annotation designates the class as a configuration
 * class for Spring that defines beans.
 */
@Configuration
/**
 * This annotation enables Spring Security and allows customization
 * of security by defining beans and configurations in this class.
 */
@EnableWebSecurity
public class CustomerConfiguration {

    /**
     * This is the main interface in Spring Security for loading
     * user information. You provide your own implementation of
     * this interface (CustomerServiceConfig) that will be used
     * to load users from the database or other source.
     * @return UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomerServiceConfig();
    }

    /**
     * This bean is used to encode passwords before they
     * are stored in the database and to validate users on login.
     * @return passwordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * This is the main mechanism for defining the security of your application.
     * You configure how HTTP requests should be secured, which paths are allowed
     * for all and which require authentication.
     * @param http
     * @throws Exception
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
                /**
                 * Disable CSRF protection, which may be appropriate if you use security tokens, but care should be taken.
                 * Access to static resources and public paths (such as the main page and product details) is allowed for everyone.
                 * Paths related to shopping and searching for products are only allowed for users with the "CUSTOMER" role.
                 */
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(author ->
                        author.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers("/*", "/product-detail/**").permitAll()
                                .requestMatchers("/shop/**", "/find-products/**").hasAuthority("CUSTOMER")
                )
                /**
                 *  Configure a login form with a custom login page (/login), a login processing URL (/do-login), and a successful login URL (/index).
                 */
                .formLogin(login ->
                        login.loginPage("/login")
                                .loginProcessingUrl("/do-login")
                                .defaultSuccessUrl("/index", true)
                                .permitAll()
                )
                /**
                 * You configure an exit that deletes the session and clears the certificate, then redirects to the login page with the logout parameter.
                 */
                .logout(logout ->
                        logout.invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/login?logout")
                                .permitAll()
                )
                .authenticationManager(authenticationManager)
                /**
                 * You set a session creation policy that always creates a new session on authentication.
                 */
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                )
        ;
        return http.build();
    }

    /**
     * This method configures the locale of the application. Languages!
     * @return
     */
    /* @Bean
    public LocaleResolver localeResolver(){
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(new Locale("bg"));
        return cookieLocaleResolver;
    }
    */

    /*@Bean
    public LocaleResolver LocaleResolver(){
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);

        return slr;
    }*/
    /*
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }*/

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(new Locale("en")); // Default to Bulgarian
        cookieLocaleResolver.setCookieName("lang"); // Stores language preference in a cookie
        cookieLocaleResolver.setCookieMaxAge(3600); // Cookie duration (optional)
        return cookieLocaleResolver;
    }


    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang"); // URL parameter to switch languages
        registry.addInterceptor(localeChangeInterceptor);
    }

    //@Override
    /*
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
    */


}
