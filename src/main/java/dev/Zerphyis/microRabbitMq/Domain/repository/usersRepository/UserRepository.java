package dev.Zerphyis.microRabbitMq.Domain.repository.usersRepository;

import dev.Zerphyis.microRabbitMq.Domain.model.users.Users;

import java.util.Optional;

public interface UserRepository {
    Optional<Users> findByEmail(String email);
    Users save(Users user);
    Optional<Users> findById(Long id);
    void deleteById(Long id);
}
