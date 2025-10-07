package dev.Zerphyis.microRabbitMq.Application.dto.users;

import lombok.Data;

@Data
public class UserLoginResponseDto {
    private String token;
    private String email;

}
