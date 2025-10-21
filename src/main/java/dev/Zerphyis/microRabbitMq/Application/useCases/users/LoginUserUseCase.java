package dev.Zerphyis.microRabbitMq.Application.useCases.users;

import dev.Zerphyis.microRabbitMq.Application.dto.users.UserLoginDto;
import dev.Zerphyis.microRabbitMq.Application.dto.users.UserLoginResponseDto;
import dev.Zerphyis.microRabbitMq.Infra.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class LoginUserUseCase {

    private final JwtTokenProvider jwtProvider;
    private AuthenticationManager manager;

    public LoginUserUseCase(JwtTokenProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    public void setAuthenticationManager(AuthenticationManager manager) {
        this.manager = manager;
    }

    public UserLoginResponseDto execute(UserLoginDto loginDto) {
        if (manager == null) {
            throw new IllegalStateException("AuthenticationManager não foi injetado");
        }

        Authentication auth = manager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        String jwt = jwtProvider.generateToken(auth);
        return new UserLoginResponseDto(jwt, loginDto.getEmail());
    }
}
