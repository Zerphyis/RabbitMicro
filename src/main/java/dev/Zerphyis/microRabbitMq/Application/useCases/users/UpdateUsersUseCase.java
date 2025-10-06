package dev.Zerphyis.microRabbitMq.Application.useCases.users;

import dev.Zerphyis.microRabbitMq.Application.dto.users.UserResponseDto;
import dev.Zerphyis.microRabbitMq.Application.dto.users.UsersRegisterDto;
import dev.Zerphyis.microRabbitMq.Domain.model.users.Users;
import dev.Zerphyis.microRabbitMq.Domain.repository.usersRepository.UserRepository;
import dev.Zerphyis.microRabbitMq.Infra.exceptions.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UpdateUsersUseCase {
    private final UserRepository repository;
    private final PasswordEncoder enconder;

    public UpdateUsersUseCase(UserRepository repository, PasswordEncoder enconder) {
        this.repository = repository;
        this.enconder = enconder;
    }

    public UserResponseDto execute(Long id, UsersRegisterDto updateDatas) {
        Users user = repository.findById(id)
                .filter(Users::isActive).
                orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        user.setName(updateDatas.getName());
        user.setEmail(updateDatas.getEmail());

        if (updateDatas.getPassword() != null && !updateDatas.getPassword().isBlank()) {
            user.setPassword(enconder.encode(updateDatas.getPassword()));
        }

        Users updatedUser = repository.save(user);

        return new UserResponseDto(updatedUser.getName(),updatedUser.getEmail());
    }
}
