package br.com.oxy.ssgt.infra.controller.user;

import br.com.oxy.ssgt.application.usecases.CreateUser;
import br.com.oxy.ssgt.application.usecases.ListUsers;
import br.com.oxy.ssgt.domain.entities.user.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final CreateUser createUser;
    private final ListUsers listUsers;
    public UserController(CreateUser createUser, ListUsers listUsers) {
        this.createUser = createUser;
        this.listUsers = listUsers;
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO dto) {
        User user =  createUser.registerUser(new User(dto.name(), dto.email(), dto.password()));
        return new UserDTO(user.getName(), user.getEmail(), user.getPassword());
    }

    @GetMapping
    public List<UserDTO> getAllUserDTOS() {
        return listUsers.getAllUsers().stream()
                .map(user -> new UserDTO(user.getName(), user.getEmail(), user.getPassword()))
                .collect(Collectors.toList());
    }
}
