package br.com.oxy.ssgt.application.projectcases;

import br.com.oxy.ssgt.application.gateways.ProjectRepositoryApplication;
import br.com.oxy.ssgt.domain.entities.project.Project;
import br.com.oxy.ssgt.infra.execption.BusinessException;

public class UpdateProject {

    private final ProjectRepositoryApplication repository;

    public UpdateProject(ProjectRepositoryApplication repository) {
        this.repository = repository;
    }

    public Project updateProject(Long currentUserId, Project project) {
        boolean isAdmin = repository.isAdmin(
                currentUserId,
                project.getId()
        );

        if(!isAdmin) {
            throw new BusinessException("User does not have permission to update this project.");
        }
        return repository.updateProject(project);
    }
}
