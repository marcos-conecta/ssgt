package br.com.oxy.ssgt.application.usecases;

import br.com.oxy.ssgt.application.gateways.UserRepositoryApplication;

public class DeleteUser {

    private final UserRepositoryApplication repository;

    public DeleteUser(UserRepositoryApplication repository) {
        this.repository = repository;
    }

    public void deleteUser(Long id) {
        repository.deleteUser(id);
    }
}
