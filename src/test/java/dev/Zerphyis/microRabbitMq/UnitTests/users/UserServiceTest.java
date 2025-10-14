package dev.Zerphyis.microRabbitMq.UnitTests.users;

import dev.Zerphyis.microRabbitMq.Application.dto.users.*;
import dev.Zerphyis.microRabbitMq.Application.mapper.user.UserMapper;
import dev.Zerphyis.microRabbitMq.Application.services.user.UserService;
import dev.Zerphyis.microRabbitMq.Application.useCases.users.*;
import dev.Zerphyis.microRabbitMq.Domain.model.users.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private RegisterUsersUseCase registerUseCase;
    private LoginUserUseCase loginUseCase;
    private LogoutUserUseCase logoutUseCase;
    private FindUsersUseCase findUserUseCase;
    private UpdateUsersUseCase updateUseCase;
    private DeactiveUserUseCase deactiveUseCase;
    private UserMapper mapper;
    private UserService service;

    @BeforeEach
    void setup() {
        registerUseCase = mock(RegisterUsersUseCase.class);
        loginUseCase = mock(LoginUserUseCase.class);
        logoutUseCase = mock(LogoutUserUseCase.class);
        findUserUseCase = mock(FindUsersUseCase.class);
        updateUseCase = mock(UpdateUsersUseCase.class);
        deactiveUseCase = mock(DeactiveUserUseCase.class);
        mapper = mock(UserMapper.class);

        service = new UserService(registerUseCase, loginUseCase, logoutUseCase,
                findUserUseCase, updateUseCase, deactiveUseCase, mapper);
    }

    @Test
    @DisplayName("Deve registrar usuário com sucesso")
    void register_success() {
        UsersRegisterDto dto = new UsersRegisterDto();
        UserResponseDto responseDto = new UserResponseDto("John", "john@example.com");

        when(registerUseCase.execute(dto)).thenReturn(responseDto);

        UserResponseDto result = service.register(dto);

        assertEquals("John", result.getName());
        assertEquals("john@example.com", result.getEmail());
        verify(registerUseCase).execute(dto);
    }

    @Test
    @DisplayName("Deve autenticar usuário com sucesso e retornar token")
    void login_success() {
        UserLoginDto dto = new UserLoginDto();
        UserLoginResponseDto loginResponse = new UserLoginResponseDto("jwt-token", "john@example.com");

        when(loginUseCase.execute(dto)).thenReturn(loginResponse);

        String token = service.login(dto);

        assertEquals("jwt-token", token);
        verify(loginUseCase).execute(dto);
    }

    @Test
    @DisplayName("Deve fazer logout do usuário com sucesso")
    void logout_success() {
        String token = "jwt-token";

        service.logout(token);

        verify(logoutUseCase).execute(argThat(logoutDto -> logoutDto.getToken().equals(token)));
    }

    @Test
    @DisplayName("Deve encontrar usuário pelo ID com sucesso")
    void findById_success() {
        Long id = 1L;
        UserResponseDto responseDto = new UserResponseDto("Alice", "alice@example.com");

        when(findUserUseCase.byId(id)).thenReturn(responseDto);

        UserResponseDto result = service.findById(id);

        assertEquals("Alice", result.getName());
        assertEquals("alice@example.com", result.getEmail());
        verify(findUserUseCase).byId(id);
    }

    @Test
    @DisplayName("Deve encontrar usuários pelo nome com sucesso")
    void findByName_success() {
        String name = "Bob";
        List<UserResponseDto> responseList = List.of(new UserResponseDto("Bob", "bob@example.com"));

        when(findUserUseCase.byName(name)).thenReturn(responseList);

        List<UserResponseDto> result = service.findByName(name);

        assertEquals(1, result.size());
        assertEquals("Bob", result.get(0).getName());
        verify(findUserUseCase).byName(name);
    }

    @Test
    @DisplayName("Deve atualizar usuário com sucesso")
    void update_success() {
        Long id = 1L;
        UsersRegisterDto dto = new UsersRegisterDto();
        UserResponseDto updatedDto = new UserResponseDto("Charlie", "charlie@example.com");

        when(updateUseCase.execute(id, dto)).thenReturn(updatedDto);

        UserResponseDto result = service.update(id, dto);

        assertEquals("Charlie", result.getName());
        assertEquals("charlie@example.com", result.getEmail());
        verify(updateUseCase).execute(id, dto);
    }

    @Test
    @DisplayName("Deve desativar usuário com sucesso")
    void deactivate_success() {
        Long id = 1L;

        service.deactivate(id);

        verify(deactiveUseCase).execute(id);
    }

    @Test
    @DisplayName("Deve carregar usuário pelo username com sucesso")
    void loadUserByUsername_success() {
        String email = "dave@example.com";
        Users user = new Users();
        UserDetails userDetails = mock(UserDetails.class);

        when(findUserUseCase.byEmail(email)).thenReturn(user);
        when(mapper.toUserDetails(user)).thenReturn(userDetails);

        UserDetails result = service.loadUserByUsername(email);

        assertEquals(userDetails, result);
        verify(findUserUseCase).byEmail(email);
        verify(mapper).toUserDetails(user);
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não for encontrado pelo username")
    void loadUserByUsername_notFound() {
        String email = "notfound@example.com";

        when(findUserUseCase.byEmail(email)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(email));
        verify(findUserUseCase).byEmail(email);
        verifyNoInteractions(mapper);
    }
}
