package dev.Zerphyis.microRabbitMq.Application.useCases.users;

import dev.Zerphyis.microRabbitMq.Application.dto.users.UserLogoutDto;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class LogoutUserUseCase {
    private final Set<String> tokenBlacklist = ConcurrentHashMap.newKeySet();

    public void execute(UserLogoutDto logoutRequestDto) {
        String token = logoutRequestDto.getToken();
        if (token != null && !token.isBlank()) {
            tokenBlacklist.add(token);
        }
    }

    public boolean isTokenBlacklisted(String token) {
        return tokenBlacklist.contains(token);
    }
}
