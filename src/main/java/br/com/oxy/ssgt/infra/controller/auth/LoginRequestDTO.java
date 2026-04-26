package br.com.oxy.ssgt.infra.controller.auth;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginRequestDTO(
        @Schema(description = "User's email address", example = "joao@exemplo.com")
        String email,
        @Schema(description = "User's password", example = "123456")
        String password
) {
}
