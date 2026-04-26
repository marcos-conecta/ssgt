package br.com.oxy.ssgt.infra.controller.user;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserDTO(
        @Schema(description = "User's full name", example = "João Silva")
        @NotBlank
        String name,
        @Schema(description = "User's email address", example = "joao@exemplo.com")
        @NotBlank
        @Email
        String email,
        @Schema(description = "User's password", example = "123456")
        @NotBlank
        String password
) {
}

