package br.com.oxy.ssgt.infra.controller.user;

import br.com.oxy.ssgt.application.usecases.*;
import br.com.oxy.ssgt.domain.entities.user.User;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    private final CreateUser createUser;
    private final ListUsers listUsers;
    private final FindUserById findUserById;
    private final UpdateUser updateUser;
    private final DeleteUser deleteUser;
    public UserController(CreateUser createUser, ListUsers listUsers, FindUserById findUserById, UpdateUser updateUser, DeleteUser deleteUser) {
        this.createUser = createUser;
        this.listUsers = listUsers;
        this.findUserById = findUserById;
        this.updateUser = updateUser;
        this.deleteUser = deleteUser;
    }

    @Operation(summary = "Create a new user", description = "Registers a new user in the system.")
    @PostMapping
    public UserDTO createUser(@RequestBody @Valid CreateUserDTO dto) {
        User user =  createUser.registerUser(
                new User(null, dto.name(), dto.email(), dto.password())
        );
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    @Operation(summary = "Get user by ID", description = "Retrieves a user by their unique ID.")
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        User user = findUserById.findById(id);
        return new UserDTO(user.getId(), user.getName(), user.getEmail());
    }

    @Operation(summary = "Get all users", description = "Retrieves a paginated list of all users in the system.")
    @GetMapping
    public Page <UserDTO> getAllUserDTOS(@ParameterObject Pageable pageable) {
        return listUsers.getAllUsers(pageable)
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail()));
    }

    @Operation(summary = "Update user", description = "Updates the details of an existing user.")
    @PutMapping
    public UserDTO updateUser(@RequestBody @Valid UpdateUserDTO dto) {
        User update = updateUser.updateUser(new User(dto.id(), dto.name(), dto.email(), dto.password()));

        return new UserDTO(update.getId(), update.getName(), update.getEmail());
    }

    @Operation(summary = "Delete user", description = "Deletes a user from the system by their unique ID.")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        deleteUser.deleteUser(id);
    }
}
