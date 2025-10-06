package dev.Zerphyis.microRabbitMq.Domain.repository.usersRepository.jpa;

import dev.Zerphyis.microRabbitMq.Domain.model.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepositoryJpa extends JpaRepository<Users,Long> {
    Optional<Users> findByEmail(String email);
    List<Users> findByNameContainingIgnoreCase(String name);
}
