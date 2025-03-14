package org.example.dinger.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Enable HTTP Basic Authentication
        http.httpBasic(Customizer.withDefaults());

        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/approve/{userId}").hasRole("SUPER_ADMIN")
                .requestMatchers(HttpMethod.POST, "/auth/block/{userId}").hasRole("SUPER_ADMIN")
                .requestMatchers(HttpMethod.GET, "/auth/filter").hasRole("SUPER_ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/projects/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/projects/create/{userId}").hasRole("REGISTERED_USER")
                .requestMatchers(HttpMethod.GET, "/api/projects/filter/*").hasRole("REGISTERED_USER")
                .requestMatchers(HttpMethod.PUT, "/api/projects/**").hasRole("REGISTERED_USER")
                .requestMatchers(HttpMethod.DELETE, "/api/projects/**").hasRole("REGISTERED_USER")
                .anyRequest().authenticated()
        );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
