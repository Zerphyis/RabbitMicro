package dev.Zerphyis.microRabbitMq.UnitTests.users;

import dev.Zerphyis.microRabbitMq.Application.dto.users.UserLogoutDto;
import dev.Zerphyis.microRabbitMq.Application.useCases.users.LogoutUserUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogoutUserUseCaseTest {

    @Test
    @DisplayName("Deve adicionar token à blacklist")
    void logoutUser_addsTokenToBlacklist() {
        LogoutUserUseCase useCase = new LogoutUserUseCase();
        UserLogoutDto dto = new UserLogoutDto();
        dto.setToken("token123");

        useCase.execute(dto);
        assertTrue(useCase.isTokenBlacklisted("token123"));
    }

    @Test
    @DisplayName("Deve ignorar tokens nulos ou em branco")
    void logoutUser_nullOrBlankToken() {
        LogoutUserUseCase useCase = new LogoutUserUseCase();

        UserLogoutDto dto1 = new UserLogoutDto();
        dto1.setToken(null);
        useCase.execute(dto1);

        UserLogoutDto dto2 = new UserLogoutDto();
        dto2.setToken("   ");
        useCase.execute(dto2);

        assertFalse(useCase.isTokenBlacklisted(null));
        assertFalse(useCase.isTokenBlacklisted("   "));
    }
}
