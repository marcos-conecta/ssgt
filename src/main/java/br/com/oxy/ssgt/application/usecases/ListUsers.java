package br.com.oxy.ssgt.application.usecases;

import br.com.oxy.ssgt.application.gateways.UserRepositoryApplication;
import br.com.oxy.ssgt.domain.entities.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ListUsers {

    private final UserRepositoryApplication repository;

    public ListUsers(UserRepositoryApplication repository) {
        this.repository = repository;
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return this.repository.getAllUsers(pageable);
    }
}
