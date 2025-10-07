package dev.Zerphyis.microRabbitMq.Application.dto.users;


import dev.Zerphyis.microRabbitMq.Domain.model.users.typeRole.TypeRole;
import lombok.Data;

@Data
public class UsersRegisterDto {
    private String name;
    private String email;
    private String password;
    private TypeRole role;
}
