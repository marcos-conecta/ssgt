package br.com.oxy.ssgt.infra.controller.user;


public record UserDTO(
        Long id,
        String name,
        String email
) {
}

