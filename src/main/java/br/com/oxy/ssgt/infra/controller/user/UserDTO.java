package br.com.oxy.ssgt.infra.controller.user;


import br.com.oxy.ssgt.domain.entities.user.User;

public record UserDTO(
        Long id,
        String name,
        String email
) {
    public UserDTO (User user) {
        this(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}

