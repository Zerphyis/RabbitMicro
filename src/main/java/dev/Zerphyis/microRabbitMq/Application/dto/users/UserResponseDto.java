package dev.Zerphyis.microRabbitMq.Application.dto.users;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserResponseDto {
    @Column(name = "nome")
    private String name;
    @Column(name = "email", nullable = false)
    private String email;
}
