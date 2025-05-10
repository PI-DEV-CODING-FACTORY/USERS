package com.example.users.config;

import com.example.users.utils.JwtAuthenticationEntryPoint;
import com.example.users.utils.JwtRequestFilter;
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
@EnableMethodSecurity(prePostEnabled = true)  // Enables @PreAuthorize, @PostAuthorize
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtRequestFilter jwtRequestFilter
    ) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Disable CSRF (not needed for stateless APIs)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()  // Swagger endpoints
                        .requestMatchers("/inscription/**").permitAll()
                        .requestMatchers("/users/**").permitAll()
                        .requestMatchers("/students/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()  // Allow login/signup
                        .requestMatchers("/api/public/**").permitAll()  // Public endpoints
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")  // Only ADMIN can access
                        .anyRequest().authenticated()  // All other requests need auth
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // No sessions
                );

        // Add JWT filter before UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig
    ) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.client.RestTemplate;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity (not recommended for production)
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/admins/**").permitAll() // Allow unauthenticated access to these endpoints
//                        .requestMatchers("/students/**").permitAll()
//                        .requestMatchers("/teachers/**").permitAll()
//                        .requestMatchers("/entreprise/add").permitAll()
//                        .requestMatchers("report/add", "report/all", "report/delete/{id}", "report/get/{id}","report/analyze/{id}","report/pending","report/analyze-all").permitAll() // Allow unauthenticated access to these endpoints
//                        .requestMatchers("/auth/login","/auth/forgot-password","/auth/reset-password","/auth/user", "/auth/validateToken").permitAll() // Allow access to the auth endpoints
//                        .requestMatchers("/users/all", "/users/add", "/users/findById", "/auth/login","/auth/reset-password", "users/findByRole", "users/update","users/delete").permitAll() // Allow unauthenticated access to these endpoints
//                        .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll() // Permit Swagger endpoints
//                        .requestMatchers("/inscription/all", "/inscription/add", "/inscription/pending","/inscription/accepted", "/inscription/rejected","/inscription/accept/{id}", "/inscription/reject/{id}","/inscription/{id}/document","/inscription/{id}/approve").permitAll() // Allow unauthenticated access to these endpoints
//                        .anyRequest().authenticated() // All other requests require authentication
//                )
//                .formLogin(form -> form.disable()) // Disable the default login form
//                .httpBasic(httpBasic -> httpBasic.disable()); // Disable basic auth
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
//}
