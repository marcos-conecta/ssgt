package br.com.oxy.ssgt.application.usecases;

import br.com.oxy.ssgt.application.gateways.UserRepositoryApplication;
import br.com.oxy.ssgt.domain.entities.user.User;

import java.util.List;

public class ListUsers {

    private final UserRepositoryApplication repository;

    public ListUsers(UserRepositoryApplication repository) {
        this.repository = repository;
    }

   public List<User> getAllUsers() {
        return this.repository.getAllUsers();
    }
}
