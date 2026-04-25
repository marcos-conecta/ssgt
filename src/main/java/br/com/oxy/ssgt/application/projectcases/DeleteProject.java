package br.com.oxy.ssgt.application.projectcases;

import br.com.oxy.ssgt.application.gateways.ProjectRepositoryApplication;


public class DeleteProject {

    private final ProjectRepositoryApplication repository;

    public DeleteProject(ProjectRepositoryApplication repository) {
        this.repository = repository;
    }

    public void deleteProject(Long id) {
        repository.deleteProject(id);
    }
}
