package br.com.oxy.ssgt.infra.controller.project;



import br.com.oxy.ssgt.domain.entities.project.Project;
import br.com.oxy.ssgt.domain.entities.user.User;

import java.util.List;

public record ProjectDTO(

        Long id,
        String name,
        String description,
        Long ownerId,
        List<Long> members

) {
        public ProjectDTO(Project project) {
            this(
                    project.getId(),
                    project.getName(),
                    project.getDescription(),
                    project.getOwner().getId(),
                    project.getMembers().stream().map(User::getId).toList()
            );
        }
}

