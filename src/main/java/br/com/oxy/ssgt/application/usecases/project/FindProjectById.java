package br.com.oxy.ssgt.application.usecases.project;

import br.com.oxy.ssgt.application.gateways.ProjectRepositoryApplication;
import br.com.oxy.ssgt.domain.entities.project.Project;

public class FindProjectById {

    private final ProjectRepositoryApplication repository;

    public FindProjectById(ProjectRepositoryApplication repository) {
        this.repository = repository;
    }

    public Project findById(Long projectId) {
        return repository.findById(projectId);
    }
}
