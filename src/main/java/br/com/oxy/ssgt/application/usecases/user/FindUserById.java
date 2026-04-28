package br.com.oxy.ssgt.application.usecases.user;

import br.com.oxy.ssgt.application.gateways.UserRepositoryApplication;
import br.com.oxy.ssgt.domain.entities.user.User;

public class FindUserById {

    private final UserRepositoryApplication repository;

    public FindUserById(UserRepositoryApplication repository) {
        this.repository = repository;
    }

    public User findById(Long userId) {
        return repository.findById(userId);
    }
}
