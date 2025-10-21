package dev.Zerphyis.microRabbitMq.Application.useCases.users;

import dev.Zerphyis.microRabbitMq.Application.dto.users.UserResponseDto;
import dev.Zerphyis.microRabbitMq.Application.dto.users.UsersRegisterDto;
import dev.Zerphyis.microRabbitMq.Domain.model.users.Users;
import dev.Zerphyis.microRabbitMq.Domain.repository.usersRepository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

public class RegisterUsersUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUsersUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDto execute(UsersRegisterDto registerDto) {
        userRepository.findByEmail(registerDto.getEmail())
                .ifPresent(u -> {
                    throw new IllegalArgumentException("E-mail já cadastrado");
                });

        Users user = Users.builder()
                .name(registerDto.getName())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .active(true)
                .role(registerDto.getRole())
                .build();

        Users savedUser = userRepository.save(user);

        return new UserResponseDto(
                savedUser.getName(),
                savedUser.getEmail()
        );
    }
}
