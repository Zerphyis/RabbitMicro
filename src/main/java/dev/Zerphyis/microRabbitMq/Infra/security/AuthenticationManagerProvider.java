package dev.Zerphyis.microRabbitMq.Infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationManagerProvider {

    @Autowired
    private AuthenticationConfiguration config;

    public AuthenticationManager get() {
        try {
            return config.getAuthenticationManager();
        } catch (Exception e) {
            throw new RuntimeException("Falha ao criar AuthenticationManager", e);
        }
    }
}
