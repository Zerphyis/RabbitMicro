package dev.Zerphyis.microRabbitMq.Application.dto.users;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UsersRegisterDto {
    @Column(name = "nome")
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "senha")
    private String password;

}
