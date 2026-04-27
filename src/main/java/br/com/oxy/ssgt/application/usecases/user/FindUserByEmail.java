package br.com.oxy.ssgt.application.usecases.user;

import br.com.oxy.ssgt.application.gateways.UserRepositoryApplication;
import br.com.oxy.ssgt.domain.entities.user.User;

public class FindUserByEmail {

    private final UserRepositoryApplication repository;

    public FindUserByEmail(UserRepositoryApplication repository) {
        this.repository = repository;
    }

    public User execute(String email) {
        return repository.findByEmail(email);
    }
}
