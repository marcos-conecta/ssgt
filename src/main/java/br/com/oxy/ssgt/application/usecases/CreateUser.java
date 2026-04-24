package br.com.oxy.ssgt.application.usecases;

import br.com.oxy.ssgt.application.gateways.UserRepositoryApplication;
import br.com.oxy.ssgt.domain.entities.user.User;

public class CreateUser {

    private final UserRepositoryApplication repository;

    public CreateUser(UserRepositoryApplication repository) {
        this.repository = repository;
    }

    public User registerUser(User user) {
        return repository.registerUser(user);
    }
}
