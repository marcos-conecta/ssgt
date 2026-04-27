package br.com.oxy.ssgt.infra.controller.user;

import br.com.oxy.ssgt.application.usecases.*;
import br.com.oxy.ssgt.domain.entities.user.User;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


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
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid CreateUserDTO dto) {
        User user =  createUser.registerUser(
                new User(null, dto.name(), dto.email(), dto.password())
        );
        URI location = URI.create("/users/" + user.getId());
        return ResponseEntity.created(location).body(new UserDTO(user));
    }

    @Operation(summary = "Get user by ID", description = "Retrieves a user by their unique ID.")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = findUserById.findById(id);
        return ResponseEntity.ok(new UserDTO(user));
    }

    @Operation(summary = "Get all users", description = "Retrieves a paginated list of all users in the system.")
    @GetMapping
    public ResponseEntity<Page <UserDTO>> getAllUserDTOS(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(listUsers.getAllUsers(pageable)
                .map(UserDTO::new));
    }

    @Operation(summary = "Update user", description = "Updates the details of an existing user.")
    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@RequestBody @Valid UpdateUserDTO dto) {
        User update = updateUser.updateUser(new User(dto.id(), dto.name(), dto.email(), dto.password()));

        return ResponseEntity.ok(new UserDTO(update));
    }

    @Operation(summary = "Delete user", description = "Deletes a user from the system by their unique ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        deleteUser.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
