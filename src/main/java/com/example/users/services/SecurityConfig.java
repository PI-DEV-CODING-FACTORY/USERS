package com.example.users.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity (not recommended for production)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("report/add", "report/all", "report/delete/{id}", "report/get/{id}").permitAll() // Allow unauthenticated access to these endpoints
                        .requestMatchers("/auth/login","/auth/forgot-password","/auth/reset-password","/auth/user", "/auth/validateToken").permitAll() // Allow access to the auth endpoints
                        .requestMatchers("/users/all", "/users/add", "/users/findById", "/auth/login","/auth/reset-password", "users/findByRole", "users/update","users/delete").permitAll() // Allow unauthenticated access to these endpoints
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll() // Permit Swagger endpoints
                        .requestMatchers("/inscription/all", "/inscription/add", "/inscription/pending","/inscription/accepted", "/inscription/rejected","/inscription/accept/{id}", "/inscription/reject/{id}","/inscription/{id}/bachelor-degree").permitAll() // Allow unauthenticated access to these endpoints
                        .anyRequest().authenticated() // All other requests require authentication
                )
                .formLogin(form -> form.disable()) // Disable the default login form
                .httpBasic(httpBasic -> httpBasic.disable()); // Disable basic auth

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
