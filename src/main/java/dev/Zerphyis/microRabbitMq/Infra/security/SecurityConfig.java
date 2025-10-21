package dev.Zerphyis.microRabbitMq.Infra.security;

import dev.Zerphyis.microRabbitMq.Application.services.user.UserService;
import dev.Zerphyis.microRabbitMq.Application.useCases.users.LoginUserUseCase;
import dev.Zerphyis.microRabbitMq.Application.useCases.users.LogoutUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final LogoutUserUseCase logoutUserUseCase;
    private final UserService userService;
    private final LoginUserUseCase loginUserUseCase;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider,
                          LogoutUserUseCase logoutUserUseCase,
                          @Lazy UserService userService,
                          LoginUserUseCase loginUserUseCase) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.logoutUserUseCase = logoutUserUseCase;
        this.userService = userService;
        this.loginUserUseCase = loginUserUseCase;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
        loginUserUseCase.setAuthenticationManager(authManager);

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/register", "/api/users/login").permitAll()
                        .requestMatchers("/producer/**").permitAll()
                        .requestMatchers("/api/users/search").hasRole("ADMIN")
                        .requestMatchers("/api/users/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .userDetailsService(userService)
                .addFilterBefore(filterSecurity(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public FilterSecurity filterSecurity() {
        return new FilterSecurity(jwtTokenProvider, logoutUserUseCase, userService);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
