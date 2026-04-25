package br.com.oxy.ssgt.config;

import br.com.oxy.ssgt.application.gateways.UserRepositoryApplication;
import br.com.oxy.ssgt.application.usecases.*;
import br.com.oxy.ssgt.infra.gateways.UserEntityMapper;
import br.com.oxy.ssgt.infra.gateways.UserRepositoryJPA;
import br.com.oxy.ssgt.infra.persistence.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserConfig {

    @Bean
    CreateUser createUser(UserRepositoryApplication repositoryUser, PasswordEncoder passwordEncoder) {
        return new CreateUser(repositoryUser, passwordEncoder);
    }

    @Bean
    ListUsers listUsers(UserRepositoryApplication repositoryUser) {
        return new ListUsers(repositoryUser);
    }

    @Bean
    FindUserById findUserById(UserRepositoryApplication repositoryUser) {
        return new FindUserById(repositoryUser);
    }

    @Bean
    UpdateUser repositoryUser(UserRepositoryApplication repositoryUser) {
        return new UpdateUser(repositoryUser);
    }

    @Bean
    DeleteUser deleteUser(UserRepositoryApplication repositoryUser) {
        return new DeleteUser(repositoryUser);
    }

    @Bean
    UserRepositoryJPA createUserRepositoryJPA(UserRepository repository, UserEntityMapper mapper) {
        return new UserRepositoryJPA(repository, mapper);
    }

    @Bean
    UserEntityMapper returnMapper() {

        return new UserEntityMapper();
    }

    @Bean
    FindUserByEmail findUserByEmail(UserRepositoryApplication repositoryUser) {
        return new FindUserByEmail(repositoryUser);
    }

}
