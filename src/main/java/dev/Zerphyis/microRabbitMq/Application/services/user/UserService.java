package dev.Zerphyis.microRabbitMq.Application.services.user;

import dev.Zerphyis.microRabbitMq.Application.dto.users.UserLoginDto;
import dev.Zerphyis.microRabbitMq.Application.dto.users.UserLogoutDto;
import dev.Zerphyis.microRabbitMq.Application.dto.users.UserResponseDto;
import dev.Zerphyis.microRabbitMq.Application.dto.users.UsersRegisterDto;
import dev.Zerphyis.microRabbitMq.Application.mapper.user.UserMapper;
import dev.Zerphyis.microRabbitMq.Application.useCases.users.*;
import dev.Zerphyis.microRabbitMq.Domain.model.users.Users;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final RegisterUsersUseCase register;
    private final LoginUserUseCase login;
    private final LogoutUserUseCase logout;
    private final FindUsersUseCase findUser;
    private final UpdateUsersUseCase update;
    private final DeactiveUserUseCase deactive;
    private final UserMapper mapper;

    public UserService(RegisterUsersUseCase register,
                       LoginUserUseCase login,
                       LogoutUserUseCase logout,
                       FindUsersUseCase findUser,
                       UpdateUsersUseCase update,
                       DeactiveUserUseCase deactive,
                       UserMapper mapper) {
        this.register = register;
        this.login = login;
        this.logout = logout;
        this.findUser = findUser;
        this.update = update;
        this.deactive = deactive;
        this.mapper = mapper;
    }


@CacheEvict(value = {"users","user"},allEntries = true)
    public UserResponseDto register(UsersRegisterDto dto) {
        return register.execute(dto);
    }


    public String login(UserLoginDto dto) {
        return login.execute(dto).getToken();
    }

    public void logout(String token) {
        UserLogoutDto logoutDto = new UserLogoutDto();
        logoutDto.setToken(token);
        logout.execute(logoutDto);
    }

    @Cacheable(value = "user", key = "#id")
    public UserResponseDto findById(Long id) {
        return findUser.byId(id);
    }

    @Cacheable(value = "users", key = "#name")
    public List<UserResponseDto> findByName(String name) {
        return findUser.byName(name)
                .stream()
                .collect(Collectors.toList());
    }

    @CachePut(value = "user", key = "#id")
    @CacheEvict(value = "users", allEntries = true)
    public UserResponseDto update(Long id, UsersRegisterDto dto) {
        return update.execute(id, dto);
    }

    @CacheEvict(value = {"user", "users"}, allEntries = true)
    public void deactivate(Long id) {
        deactive.execute(id);
    }

    @Cacheable(value = "user_by_email", key = "#username")
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = findUser.byEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com email: " + username);
        }
        return mapper.toUserDetails(user);
    }
}
