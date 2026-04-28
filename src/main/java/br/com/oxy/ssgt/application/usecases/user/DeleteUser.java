package br.com.oxy.ssgt.application.usecases.user;

import br.com.oxy.ssgt.application.gateways.UserRepositoryApplication;

public class DeleteUser {

    private final UserRepositoryApplication repository;

    public DeleteUser(UserRepositoryApplication repository) {
        this.repository = repository;
    }

    public void deleteUser(Long userId) {
        repository.deleteUser(userId);
    }
}
