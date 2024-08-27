package com.judele.raci.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/assets/**").permitAll() // Permit access to these paths without authentication
                    .anyRequest().authenticated() // All other requests need authentication
            )
            .oauth2Login(oauth2Login ->
                oauth2Login
                    .defaultSuccessUrl("/home", true) // Redirect to the homepage on successful login
            )
            .logout(logout -> logout
                .logoutUrl("/logout") // Define the URL for logout
                .logoutSuccessUrl("/") // Redirect to homepage after logout
                .invalidateHttpSession(true) // Invalidate the HTTP session
                .deleteCookies("JSESSIONID") // Delete the session cookie
                .permitAll()
            )
            .exceptionHandling(exceptionHandling ->
                exceptionHandling
                    .authenticationEntryPoint((request, response, authException) -> {
                        response.sendRedirect("/oauth2/authorization/google"); // Redirect unauthenticated users to Google login
                    })
            );

        return http.build();
    }
}
