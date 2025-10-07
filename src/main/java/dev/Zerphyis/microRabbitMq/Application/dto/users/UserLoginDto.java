package dev.Zerphyis.microRabbitMq.Application.dto.users;

import lombok.Data;

@Data
public class UserLoginDto {
    private String email;
    private String password;

}
