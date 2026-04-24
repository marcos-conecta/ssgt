package br.com.oxy.ssgt.application.gateways;

import br.com.oxy.ssgt.domain.entities.user.User;

import java.util.List;

public interface UserRepositoryApplication {

    User registerUser(User user);

    List<User> getAllUsers();
}
