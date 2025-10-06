package dev.Zerphyis.microRabbitMq.Application.useCases.users;

import dev.Zerphyis.microRabbitMq.Application.dto.users.UserResponseDto;
import dev.Zerphyis.microRabbitMq.Domain.model.users.Users;
import dev.Zerphyis.microRabbitMq.Domain.repository.usersRepository.UserRepository;
import dev.Zerphyis.microRabbitMq.Infra.exceptions.UserNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class FindUsersUseCase {
    private final UserRepository repository;

    public FindUsersUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public UserResponseDto byId(Long id){
        Users user = repository.findById(id)
                .filter(Users::isActive)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        return new UserResponseDto(user.getName(), user.getEmail());

    }

    public List<UserResponseDto> byName(String name) {
        return repository.findByNameContainingIgnoreCase(name)
                .stream()
                .filter(Users::isActive)
                .map(u -> new UserResponseDto(u.getName(), u.getEmail()))
                .collect(Collectors.toList());
    }
}
