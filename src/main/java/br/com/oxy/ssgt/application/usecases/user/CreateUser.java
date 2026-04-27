package br.com.oxy.ssgt.application.usecases.user;

import br.com.oxy.ssgt.application.gateways.UserRepositoryApplication;
import br.com.oxy.ssgt.domain.entities.user.User;
import br.com.oxy.ssgt.infra.execption.BusinessException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CreateUser {

    private final UserRepositoryApplication repository;
    private final PasswordEncoder passwordEncoder;

    public CreateUser(UserRepositoryApplication repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        if(repository.existsByEmail(user.getEmail())){
            throw new BusinessException("Email already registered. " + user.getEmail());
        }

        String encryptedPassword = passwordEncoder.encode(user.getPassword());

        User newUser = new User(
                null,
                user.getName(),
                user.getEmail(),
                encryptedPassword
        );

        return repository.registerUser(newUser);
    }

}
