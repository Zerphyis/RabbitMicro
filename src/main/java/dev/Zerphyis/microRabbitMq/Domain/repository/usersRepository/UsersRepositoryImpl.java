package dev.Zerphyis.microRabbitMq.Domain.repository.usersRepository;

import dev.Zerphyis.microRabbitMq.Domain.model.users.Users;
import dev.Zerphyis.microRabbitMq.Domain.repository.usersRepository.jpa.UsersRepositoryJpa;

import java.util.Optional;

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
}
