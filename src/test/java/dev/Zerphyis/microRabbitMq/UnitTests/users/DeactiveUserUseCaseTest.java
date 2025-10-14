package dev.Zerphyis.microRabbitMq.UnitTests.users;

import dev.Zerphyis.microRabbitMq.Application.useCases.users.DeactiveUserUseCase;
import dev.Zerphyis.microRabbitMq.Domain.model.users.Users;
import dev.Zerphyis.microRabbitMq.Domain.repository.usersRepository.UserRepository;
import dev.Zerphyis.microRabbitMq.Infra.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeactiveUserUseCaseTest {

    private UserRepository userRepository;
    private DeactiveUserUseCase useCase;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        useCase = new DeactiveUserUseCase(userRepository);
    }

    @Test
    @DisplayName("Deve desativar um usuário ativo com sucesso")
    void deactiveUser_success() {
        Users user = new Users();
        user.setActive(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        useCase.execute(1L);

        assertFalse(user.isActive());
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o usuário não for encontrado")
    void deactiveUser_userNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> useCase.execute(1L));
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Não deve salvar usuário se ele já estiver inativo")
    void deactiveUser_alreadyInactive() {
        Users user = new Users();
        user.setActive(false);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        useCase.execute(1L);

        assertFalse(user.isActive());
        verify(userRepository, never()).save(user);
    }
}
