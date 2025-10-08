package dev.Zerphyis.microRabbitMq.Domain.repository.usersRepository;

import dev.Zerphyis.microRabbitMq.Domain.model.users.Users;
import dev.Zerphyis.microRabbitMq.Domain.repository.usersRepository.jpa.UsersRepositoryJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class UsersRepositoryImpl implements UserRepository {

    private final UsersRepositoryJpa usersRepositoryJpa;

    public UsersRepositoryImpl(UsersRepositoryJpa usersRepositoryJpa) {
        this.usersRepositoryJpa = usersRepositoryJpa;
    }

    @Override
    public Optional<Users> findByEmail(String email) {
        return usersRepositoryJpa.findByEmail(email);
    }

    @Override
    public Users save(Users user) {
        return usersRepositoryJpa.save(user);
    }

    @Override
    public Optional<Users> findById(Long id) {
        return usersRepositoryJpa.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        usersRepositoryJpa.deleteById(id);
    }
    @Override
    public List<Users> findByNameContainingIgnoreCase(String name) {
        return usersRepositoryJpa.findByNameContainingIgnoreCase(name);
    }
}
