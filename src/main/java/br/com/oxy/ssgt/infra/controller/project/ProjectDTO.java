package br.com.oxy.ssgt.infra.controller.project;

import br.com.oxy.ssgt.domain.entities.project.Project;
import br.com.oxy.ssgt.domain.entities.user.User;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.List;

public record ProjectDTO(
        Long id,
        @Schema(description = "Project's name", example = "Projeto de Desenvolvimento de Software")
        String name,
        @Schema(description = "Project's description", example = "Este projeto tem como objetivo desenvolver um sistema de gerenciamento de tarefas.")
        String description,
        @Schema(description = "ID of the project owner", example = "1")
        Long ownerId,
        @Schema(description = "List of member IDs associated with the project", example = "[2, 3, 4]")
        List<Long> members

) {
    public ProjectDTO(Project project){
        this(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getOwner().getId(),
                project.getMembers().stream().map(User::getId).toList()
        );
    }
}

