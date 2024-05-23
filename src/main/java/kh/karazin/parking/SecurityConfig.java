package kh.karazin.parking;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests ->
                                               requests.requestMatchers(
                                                               "/api/auth/**", "/token/**", "/api/auth/**", "/auth/otp/**",
                                                               "/swagger-ui/**", "/v3/api-docs/**", "/actuator/**", "/main/**",
                                                               "/notifications/**", "/products/**", "/promo/**", "/billing/**"
                                                       ).permitAll()
                                                       .anyRequest().authenticated()
                )
                .build();
    }
}