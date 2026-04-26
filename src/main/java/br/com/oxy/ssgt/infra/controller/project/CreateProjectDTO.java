package br.com.oxy.ssgt.infra.controller.project;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateProjectDTO(

        @Schema(description = "Project's name", example = "Projeto de Desenvolvimento de Software")
        @NotBlank
        String name,
        @Schema(description = "Project's description", example = "Este projeto tem como objetivo desenvolver um sistema de gerenciamento de tarefas.")
        String description,
        @Schema(description = "ID of the project owner", example = "1")
        @NotNull
        Long ownerId,
        @Schema(description = "List of member IDs associated with the project", example = "[2, 3, 4]")
        List<Long> members

) {
}

