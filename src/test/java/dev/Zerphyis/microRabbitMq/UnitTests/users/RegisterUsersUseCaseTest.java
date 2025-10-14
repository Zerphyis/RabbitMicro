package dev.Zerphyis.microRabbitMq.UnitTests.users;

import dev.Zerphyis.microRabbitMq.Application.dto.users.UserResponseDto;
import dev.Zerphyis.microRabbitMq.Application.dto.users.UsersRegisterDto;
import dev.Zerphyis.microRabbitMq.Application.useCases.users.RegisterUsersUseCase;
import dev.Zerphyis.microRabbitMq.Domain.model.users.Users;
import dev.Zerphyis.microRabbitMq.Domain.model.users.typeRole.TypeRole;
import dev.Zerphyis.microRabbitMq.Domain.repository.usersRepository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegisterUsersUseCaseTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RegisterUsersUseCase useCase;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        useCase = new RegisterUsersUseCase(userRepository, passwordEncoder);
    }

    @Test
    @DisplayName("Deve registrar um novo usuário com sucesso")
    void registerUser_success() {
        UsersRegisterDto dto = new UsersRegisterDto();
        dto.setName("John");
        dto.setEmail("john@example.com");
        dto.setPassword("1234");
        dto.setRole(TypeRole.CLIENT);

        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("1234")).thenReturn("encoded1234");

        Users savedUser = new Users();
        savedUser.setName("John");
        savedUser.setEmail("john@example.com");
        when(userRepository.save(any())).thenReturn(savedUser);

        UserResponseDto response = useCase.execute(dto);

        assertEquals("John", response.getName());
        assertEquals("john@example.com", response.getEmail());
        verify(userRepository).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção se o email já existir")
    void registerUser_emailAlreadyExists() {
        UsersRegisterDto dto = new UsersRegisterDto();
        dto.setEmail("exist@example.com");

        when(userRepository.findByEmail("exist@example.com")).thenReturn(Optional.of(new Users()));

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(dto));
        verify(userRepository, never()).save(any());
    }
}
