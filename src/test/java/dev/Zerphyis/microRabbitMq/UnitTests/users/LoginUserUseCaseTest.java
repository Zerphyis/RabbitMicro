package dev.Zerphyis.microRabbitMq.UnitTests.users;

import dev.Zerphyis.microRabbitMq.Application.dto.users.UserLoginDto;
import dev.Zerphyis.microRabbitMq.Application.dto.users.UserLoginResponseDto;
import dev.Zerphyis.microRabbitMq.Application.useCases.users.LoginUserUseCase;
import dev.Zerphyis.microRabbitMq.Infra.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginUserUseCaseTest {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private LoginUserUseCase useCase;

    @BeforeEach
    void setup() {
        authenticationManager = mock(AuthenticationManager.class);
        jwtTokenProvider = mock(JwtTokenProvider.class);
        useCase = new LoginUserUseCase(authenticationManager, jwtTokenProvider);
    }

    @Test
    @DisplayName("Should authenticate user successfully and return JWT")
    void login_success() {
        UserLoginDto loginDto = new UserLoginDto();
        loginDto.setEmail("test@example.com");
        loginDto.setPassword("password123");

        Authentication authMock = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authMock);
        when(jwtTokenProvider.generateToken(authMock)).thenReturn("jwt-token-123");

        UserLoginResponseDto response = useCase.execute(loginDto);

        assertEquals("test@example.com", response.getEmail());
        assertEquals("jwt-token-123", response.getToken());
    }

    @Test
    @DisplayName("Should throw exception for invalid credentials")
    void login_invalidCredentials() {
        UserLoginDto loginDto = new UserLoginDto();
        loginDto.setEmail("wrong@example.com");
        loginDto.setPassword("wrongpass");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        assertThrows(BadCredentialsException.class, () -> useCase.execute(loginDto));
    }
}
