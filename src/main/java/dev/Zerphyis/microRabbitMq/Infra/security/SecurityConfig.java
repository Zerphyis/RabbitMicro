package dev.Zerphyis.microRabbitMq.Infra.security;

import dev.Zerphyis.microRabbitMq.Application.useCases.users.LogoutUserUseCase;
import dev.Zerphyis.microRabbitMq.Application.services.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final LogoutUserUseCase logoutUserUseCase;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider,
                          LogoutUserUseCase logoutUserUseCase) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.logoutUserUseCase = logoutUserUseCase;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   FilterSecurity filterSecurity) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/users/**/search").hasRole("ADMIN")
                        .requestMatchers("/api/users/**").hasRole("ADMIN")
                        .requestMatchers("/producer/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(filterSecurity, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public FilterSecurity filterSecurity(UserService userService) {
        return new FilterSecurity(jwtTokenProvider, logoutUserUseCase, userService);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
