package br.com.oxy.ssgt.application.usecases.user;

import br.com.oxy.ssgt.application.gateways.UserRepositoryApplication;
import br.com.oxy.ssgt.domain.entities.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreateUserTest {

    private UserRepositoryApplication repository;
    private PasswordEncoder passwordEncoder;
    private CreateUser createUser;

    @BeforeEach
    void setUp() {
        this.repository = mock(UserRepositoryApplication.class);
        this.passwordEncoder = mock(PasswordEncoder.class);
        this.createUser = new CreateUser(repository, passwordEncoder);
    }

    @Test
    void shouldCreateUserWithEncryptedPassword() {
        User input = new User(
                null,
                "João",
                "joao@email.com",
                "123456"
        );
        User saved = new User(
                1L,
                "João",
                "joao@email.com",
                "encryptedPassword"
        );

        when(passwordEncoder.encode("123456")).thenReturn("encryptedPassword");
        when(repository.registerUser(any(User.class))).thenReturn(saved);

        User result = createUser.registerUser(input);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("João");
        assertThat(result.getEmail()).isEqualTo("joao@email.com");

        verify(passwordEncoder).encode("123456");
        verify(repository).registerUser(argThat(user -> user.getPassword().equals("encryptedPassword")));
    }
}
