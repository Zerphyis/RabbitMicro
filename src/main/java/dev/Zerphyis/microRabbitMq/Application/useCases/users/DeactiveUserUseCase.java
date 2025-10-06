package dev.Zerphyis.microRabbitMq.Application.useCases.users;

import dev.Zerphyis.microRabbitMq.Domain.model.users.Users;
import dev.Zerphyis.microRabbitMq.Domain.repository.usersRepository.UserRepository;
import dev.Zerphyis.microRabbitMq.Infra.exceptions.UserNotFoundException;

public class DeactiveUserUseCase {
    private final UserRepository repository;

    public DeactiveUserUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public  void execute(Long id){
        Users user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        if (!user.isActive()) return;
        user.setActive(false);
        repository.save(user);
    }
}
