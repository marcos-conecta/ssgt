package br.com.oxy.ssgt.infra.security;

public record LoginRequestDTO(
        String email,
        String password
) {
}
