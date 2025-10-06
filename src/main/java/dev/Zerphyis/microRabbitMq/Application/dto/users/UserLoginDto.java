package dev.Zerphyis.microRabbitMq.Application.dto.users;

import jakarta.persistence.Column;

public class UserLoginDto {
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "senha")
    private String password;

}
