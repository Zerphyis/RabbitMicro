package dev.Zerphyis.microRabbitMq.UnitTests.users;

import dev.Zerphyis.microRabbitMq.Application.dto.users.UserResponseDto;
import dev.Zerphyis.microRabbitMq.Application.useCases.users.FindUsersUseCase;
import dev.Zerphyis.microRabbitMq.Domain.model.users.Users;
import dev.Zerphyis.microRabbitMq.Domain.repository.usersRepository.UserRepository;
import dev.Zerphyis.microRabbitMq.Infra.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindUsersUseCaseTest {

    private UserRepository userRepository;
    private FindUsersUseCase useCase;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        useCase = new FindUsersUseCase(userRepository);
    }

    @Test
    @DisplayName("Deve encontrar usuário pelo ID com sucesso")
    void findUserById_success() {
        Users user = new Users();
        user.setActive(true);
        user.setName("John");
        user.setEmail("john@example.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponseDto response = useCase.byId(1L);

        assertEquals("John", response.getName());
        assertEquals("john@example.com", response.getEmail());
    }

    @Test
    @DisplayName("Deve lançar exceção se o ID do usuário não for encontrado")
    void findUserById_notFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> useCase.byId(1L));
    }

    @Test
    @DisplayName("Deve encontrar usuários pelo nome com sucesso")
    void findUserByName_success() {
        Users user = new Users();
        user.setActive(true);
        user.setName("Alice");
        user.setEmail("alice@example.com");
        when(userRepository.findByNameContainingIgnoreCase("Ali")).thenReturn(List.of(user));

        List<UserResponseDto> list = useCase.byName("Ali");

        assertEquals(1, list.size());
        assertEquals("Alice", list.get(0).getName());
    }

    @Test
    @DisplayName("Deve encontrar usuário pelo email com sucesso")
    void findUserByEmail_success() {
        Users user = new Users();
        user.setActive(true);
        user.setEmail("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        Users found = useCase.byEmail("test@example.com");

        assertEquals(user, found);
    }

    @Test
    @DisplayName("Deve retornar null para usuário inativo pelo email")
    void findUserByEmail_inactive_returnsNull() {
        Users user = new Users();
        user.setActive(false);
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        Users found = useCase.byEmail("test@example.com");

        assertNull(found);
    }
}
