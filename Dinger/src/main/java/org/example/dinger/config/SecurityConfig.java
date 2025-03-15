package org.example.dinger.config;

import lombok.RequiredArgsConstructor;
import org.example.dinger.jwt.JwtAuthenticationEntryPoint;
import org.example.dinger.jwt.JwtAuthenticationFilter;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public SecurityConfig(UserDetailsService userDetailsService,JwtAuthenticationFilter jwtAuthenticationFilter, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
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
                .requestMatchers(HttpMethod.GET, "/api/projects/**").hasAnyRole("SUPER_ADMIN","REGISTERED_USER")
                .requestMatchers(HttpMethod.POST, "/api/projects/create/**").hasRole("REGISTERED_USER")
                .requestMatchers(HttpMethod.GET, "/api/projects/filter/**").hasRole("REGISTERED_USER")
                .requestMatchers(HttpMethod.PUT, "/api/projects/**").hasRole("REGISTERED_USER")
                .requestMatchers(HttpMethod.DELETE, "/api/projects/**").hasRole("REGISTERED_USER")
                .anyRequest().authenticated()
        );
        http.exceptionHandling(exception -> {
            exception
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint);
        });
        http.addFilterBefore(jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
