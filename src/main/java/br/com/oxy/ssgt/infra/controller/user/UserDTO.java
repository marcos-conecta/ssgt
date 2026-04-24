package br.com.oxy.ssgt.infra.controller.user;

public record UserDTO(
        String name,
        String email,
        String password
) {
}

