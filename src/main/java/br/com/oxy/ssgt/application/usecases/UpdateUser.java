package br.com.oxy.ssgt.application.usecases;

import br.com.oxy.ssgt.application.gateways.UserRepositoryApplication;
import br.com.oxy.ssgt.domain.entities.user.User;

public class UpdateUser {

    private final UserRepositoryApplication repository;

    public UpdateUser(UserRepositoryApplication repository) {
        this.repository = repository;
    }

    public User updateUser(User user) { return repository.updateUser(user);
    }
}
