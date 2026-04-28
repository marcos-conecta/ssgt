package br.com.oxy.ssgt.application.usecases.project;

import br.com.oxy.ssgt.application.gateways.ProjectRepositoryApplication;
import br.com.oxy.ssgt.domain.entities.project.Project;
import br.com.oxy.ssgt.infra.execption.BusinessException;

public class UpdateProject {

    private final ProjectRepositoryApplication repository;

    public UpdateProject(ProjectRepositoryApplication repository) {
        this.repository = repository;
    }

    public Project updateProject(Project project, Long currentUserId) {
        boolean isAdmin = repository.isAdmin(
                project.getId(),
                currentUserId
        );

        if(!isAdmin) {
            throw new BusinessException("User does not have permission to update this project.");
        }
        return repository.updateProject(project);
    }
}
