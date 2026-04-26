package br.com.oxy.ssgt.infra.controller.user;


import io.swagger.v3.oas.annotations.media.Schema;

public record UserDTO(
        @Schema(description = "User's unique identifier", example = "1")
        Long id,
        @Schema(description = "User's full name", example = "João Silva")
        String name,
        @Schema(description = "User's email address", example = "joao@exemplo.com")
        String email,
        @Schema(description = "User's password", example = "senha123")
        String password
) {
}

