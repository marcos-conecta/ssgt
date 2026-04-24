package br.com.oxy.ssgt.config;

import br.com.oxy.ssgt.application.gateways.UserRepositoryApplication;
import br.com.oxy.ssgt.application.usecases.CreateUser;
import br.com.oxy.ssgt.application.usecases.ListUsers;
import br.com.oxy.ssgt.infra.gateways.UserEntityMapper;
import br.com.oxy.ssgt.infra.gateways.UserRepositoryJPA;
import br.com.oxy.ssgt.infra.persistence.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    CreateUser createUser(UserRepositoryApplication repositoryUser) {
        return new CreateUser(repositoryUser);
    }

    @Bean
    ListUsers listUsers(UserRepositoryApplication repositoryUser) {
        return new ListUsers(repositoryUser);
    }

    @Bean
    UserRepositoryJPA createUserRepositoryJPA(UserRepository repository, UserEntityMapper mapper) {
        return new UserRepositoryJPA(repository, mapper);
    }

    @Bean
    UserEntityMapper returnMapper() {
        return new UserEntityMapper();
    }
}
