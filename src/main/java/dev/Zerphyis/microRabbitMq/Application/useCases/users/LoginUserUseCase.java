package dev.Zerphyis.microRabbitMq.Application.useCases.users;

import dev.Zerphyis.microRabbitMq.Application.dto.users.UserLoginDto;
import dev.Zerphyis.microRabbitMq.Application.dto.users.UserLoginResponseDto;
import dev.Zerphyis.microRabbitMq.Infra.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class LoginUserUseCase {

    private final AuthenticationManager manager;
    private final JwtTokenProvider jwtProvider;

    public LoginUserUseCase(AuthenticationManager manager, JwtTokenProvider jwtProvider) {
        this.manager = manager;
        this.jwtProvider = jwtProvider;
    }

    public UserLoginResponseDto execute(UserLoginDto loginDto) {
        Authentication auth = manager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        String jwt = jwtProvider.generateToken(auth);

        return new UserLoginResponseDto(jwt, loginDto.getEmail());
    }
}
