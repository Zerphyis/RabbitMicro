package dev.Zerphyis.microRabbitMq.Application.mapper.user;

import dev.Zerphyis.microRabbitMq.Application.dto.users.UserLoginDto;
import dev.Zerphyis.microRabbitMq.Application.dto.users.UserResponseDto;
import dev.Zerphyis.microRabbitMq.Application.dto.users.UsersRegisterDto;
import dev.Zerphyis.microRabbitMq.Domain.model.users.Users;
import dev.Zerphyis.microRabbitMq.Domain.model.users.typeRole.TypeRole;
import org.springframework.security.core.userdetails.UserDetails;

public class UserMapper {

    public Users toEntity(UsersRegisterDto dto) {
        if (dto == null) return null;

        return Users.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(dto.getRole() != null ? dto.getRole() : TypeRole.CLIENT)
                .active(true)
                .build();
    }

    public UserResponseDto toResponseDto(Users user) {
        if (user == null) return null;
        return new UserResponseDto(user.getName(), user.getEmail());
    }

    public Users toEntity(UserLoginDto dto) {
        if (dto == null) return null;
        Users user = new Users();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

    public UserDetails toUserDetails(Users user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
