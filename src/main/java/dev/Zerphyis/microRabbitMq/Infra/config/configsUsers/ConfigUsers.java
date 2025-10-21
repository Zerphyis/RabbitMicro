package dev.Zerphyis.microRabbitMq.Infra.config.configsUsers;

import dev.Zerphyis.microRabbitMq.Application.mapper.user.UserMapper;
import dev.Zerphyis.microRabbitMq.Application.services.user.UserService;
import dev.Zerphyis.microRabbitMq.Application.useCases.users.*;
import dev.Zerphyis.microRabbitMq.Domain.repository.usersRepository.UserRepository;
import dev.Zerphyis.microRabbitMq.Infra.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ConfigUsers {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RegisterUsersUseCase registerUsersUseCase(UserRepository repository, PasswordEncoder encoder){
        return new RegisterUsersUseCase(repository, encoder);
    }

    @Bean
    public LoginUserUseCase loginUserUseCase(JwtTokenProvider jwtTokenProvider) {
        return new LoginUserUseCase(jwtTokenProvider);
    }

    @Bean
    public LogoutUserUseCase logoutUserUseCase() {
        return new LogoutUserUseCase();
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

    @Bean
    public UserMapper userMapper() {
        return new UserMapper();
    }

    @Bean
    public UserService userService(RegisterUsersUseCase registerUsersUseCase,
                                   LoginUserUseCase loginUserUseCase,
                                   LogoutUserUseCase logoutUserUseCase,
                                   FindUsersUseCase findUserUseCase,
                                   UpdateUsersUseCase updateUserUseCase,
                                   DeactiveUserUseCase deactivateUserUseCase,
                                   UserMapper userMapper) {
        return new UserService(
                registerUsersUseCase,
                loginUserUseCase,
                logoutUserUseCase,
                findUserUseCase,
                updateUserUseCase,
                deactivateUserUseCase,
                userMapper
        );
    }
}
