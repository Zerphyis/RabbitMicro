package dev.Zerphyis.microRabbitMq.UnitTests.users;

import dev.Zerphyis.microRabbitMq.Application.dto.users.UserResponseDto;
import dev.Zerphyis.microRabbitMq.Application.dto.users.UsersRegisterDto;
import dev.Zerphyis.microRabbitMq.Application.useCases.users.UpdateUsersUseCase;
import dev.Zerphyis.microRabbitMq.Domain.model.users.Users;
import dev.Zerphyis.microRabbitMq.Domain.repository.usersRepository.UserRepository;
import dev.Zerphyis.microRabbitMq.Infra.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateUsersUseCaseTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UpdateUsersUseCase useCase;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        useCase = new UpdateUsersUseCase(userRepository, passwordEncoder);
    }

    @Test
    @DisplayName("Deve atualizar usuário com sucesso")
    void updateUser_success() {
        UsersRegisterDto dto = new UsersRegisterDto();
        dto.setName("NewName");
        dto.setEmail("new@example.com");
        dto.setPassword("newpass");

        Users user = new Users();
        user.setActive(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("newpass")).thenReturn("encodedNewPass");
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        UserResponseDto response = useCase.execute(1L, dto);

        assertEquals("NewName", response.getName());
        assertEquals("new@example.com", response.getEmail());
        assertEquals("encodedNewPass", user.getPassword());
    }

    @Test
    @DisplayName("Deve lançar exceção se o usuário não for encontrado")
    void updateUser_userNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UsersRegisterDto dto = new UsersRegisterDto();
        assertThrows(UserNotFoundException.class, () -> useCase.execute(1L, dto));
    }
}
