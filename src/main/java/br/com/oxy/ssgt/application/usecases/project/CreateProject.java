package br.com.oxy.ssgt.application.usecases.project;

import br.com.oxy.ssgt.application.gateways.ProjectRepositoryApplication;
import br.com.oxy.ssgt.domain.entities.project.Project;

public class CreateProject {

    private final ProjectRepositoryApplication repository;

    public CreateProject(ProjectRepositoryApplication repository) {
        this.repository = repository;
    }

    public Project registerProject(Project project) {
        return repository.registerProject(project);
    }
}
