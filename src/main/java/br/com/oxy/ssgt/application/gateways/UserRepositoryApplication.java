package br.com.oxy.ssgt.application.gateways;

import br.com.oxy.ssgt.domain.entities.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryApplication {

    User registerUser(User user);

    Page<User> getAllUsers(Pageable pageable);

    User findById(Long id);

    void deleteUser(Long id);

    User updateUser(User user);
}
