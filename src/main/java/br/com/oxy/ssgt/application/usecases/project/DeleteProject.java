package br.com.oxy.ssgt.application.usecases.project;

import br.com.oxy.ssgt.application.gateways.ProjectRepositoryApplication;
import br.com.oxy.ssgt.infra.execption.BusinessException;


public class DeleteProject {

    private final ProjectRepositoryApplication repository;

    public DeleteProject(ProjectRepositoryApplication repository) {
        this.repository = repository;
    }

    public void deleteProject(Long projectId, Long currentUserId) {

        boolean isAdmin = repository.isAdmin(projectId, currentUserId);

        if(!isAdmin){
            throw new BusinessException("User does not have permission to delete this project");
        }
        repository.deleteProject(projectId);
    }
}
