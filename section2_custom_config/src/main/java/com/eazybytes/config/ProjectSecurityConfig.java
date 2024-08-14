package com.eazybytes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {

    /**
     * The default bean is configured at SpringBootWebSecurityConfiguration
     * The default configuration for web security. It relies on Spring Security's
     * content-negotiation strategy to determine what sort of authentication to use. If
     * the user specifies their own {@link SecurityFilterChain} bean, this will back-off
     * completely and the users should specify all the bits that they want to configure as
     * part of the custom security configuration.
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        /*http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());*/
        /*http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());*/
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
                .requestMatchers("/notices", "/contact", "/error").permitAll()
        );
        // Either of them can be used, formLogin or httpBasic
        // This is determined by the content-negotiation strategy ^
        // formLogin and httpBasic must be configured explicitly, if not mentioned, they are disabled
        // They can bs disabled explicitly also: http.formLogin(AbstractHttpConfigurer::disable);
        // formLogin activates UsernamePasswordAuthenticationFilter
        // httpBasic activates BasicAuthenticationFilter
        http.formLogin(withDefaults()); // when enabled and matched, credentials extraction (object of username and password) happens in UsernamePasswordAuthenticationFilter
        // basic authentication is to send username and password, comma separated, in a base64 encoded value in the Authorization header, prefixed by Basic.
        http.httpBasic(withDefaults()); // when enabled and matched, credentials extraction (Authorization: Basic xxxxx) happens in BasicAuthenticationFilter
        return http.build();
    }

}
