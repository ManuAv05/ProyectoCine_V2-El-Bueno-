package _DAM.Cine_V2.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter; // <--- Inyectamos nuestro filtro

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(c -> c.disable()) // API Rest pura, no cookies
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // No guardar estado
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()           // Login/Register público
                        .requestMatchers("/api/v1/test/publico").permitAll() // Test público
                        .requestMatchers("/error").permitAll()             // No ocultar errores reales 500 con un 403
                        .anyRequest().authenticated()                      // Todo lo demás cerrado
                )
                // Ponemos nuestro filtro ANTES del filtro de login clásico (el ancla)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
