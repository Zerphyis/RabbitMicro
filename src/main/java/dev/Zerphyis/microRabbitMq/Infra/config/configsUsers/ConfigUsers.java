package dev.Zerphyis.microRabbitMq.Infra.config.configsUsers;

import dev.Zerphyis.microRabbitMq.Application.useCases.users.DeactiveUserUseCase;
import dev.Zerphyis.microRabbitMq.Application.useCases.users.FindUsersUseCase;
import dev.Zerphyis.microRabbitMq.Application.useCases.users.RegisterUsersUseCase;
import dev.Zerphyis.microRabbitMq.Application.useCases.users.UpdateUsersUseCase;
import dev.Zerphyis.microRabbitMq.Domain.repository.usersRepository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ConfigUsers {

    @Bean
    public RegisterUsersUseCase registerUsersUseCase(UserRepository repository, PasswordEncoder encoder){
        return  new RegisterUsersUseCase(repository,encoder);
    }

    @Bean
    public FindUsersUseCase findUserUseCase(UserRepository repository) {
        return new FindUsersUseCase(repository);
    }

    @Bean
    public UpdateUsersUseCase updateUserUseCase(UserRepository repository, PasswordEncoder encoder) {
        return new UpdateUsersUseCase(repository, encoder);
    }

    @Bean
    public DeactiveUserUseCase deactivateUserUseCase(UserRepository repository) {
        return new DeactiveUserUseCase(repository);
    }
}
