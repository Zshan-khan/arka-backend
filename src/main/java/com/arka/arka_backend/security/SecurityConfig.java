package com.arka.arka_backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sess -> 
                sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth
                // 1. System & Health Check endpoints
                .requestMatchers("/", "/error", "/favicon.ico").permitAll()

                // 2. Open Admin AUTH endpoints
                .requestMatchers("/api/admin/login", "/api/admin/register").permitAll()

                // 3. Open PUBLIC Car Viewing
                // This allows the gallery to load on your frontend
                .requestMatchers(HttpMethod.GET, "/api/cars").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/cars/**").permitAll()

                // 4. Secure Car MANAGEMENT & Admin Actions
                // Matches your CarController's POST, PUT, and DELETE routes
                .requestMatchers(HttpMethod.POST, "/api/cars/upload").hasRole("SUPER_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/cars/**").hasRole("SUPER_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/cars/**").hasRole("SUPER_ADMIN")
                .requestMatchers("/api/admin/**").hasRole("SUPER_ADMIN")

                // 5. Open Contact
                .requestMatchers("/api/contact").permitAll()

                // 6. Catch-all
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "https://arka-frontend.vercel.app", // Your production frontend
                "https://arka-backend-28zq.onrender.com"
        ));

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}