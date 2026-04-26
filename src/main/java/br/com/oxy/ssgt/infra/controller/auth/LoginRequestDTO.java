package br.com.oxy.ssgt.infra.controller.auth;

public record LoginRequestDTO(
        String email,
        String password
) {
}
