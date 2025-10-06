package dev.Zerphyis.microRabbitMq.Application.dto.users;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginResponseDto {
    private String token;
    private String email;

}
