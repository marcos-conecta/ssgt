package br.com.oxy.ssgt.application.projectcases;

import br.com.oxy.ssgt.application.gateways.ProjectRepositoryApplication;
import br.com.oxy.ssgt.domain.entities.project.Project;

public class UpdateProject {

    private final ProjectRepositoryApplication repository;

    public UpdateProject(ProjectRepositoryApplication repository) {
        this.repository = repository;
    }

    public Project updateProject(Project project) { return repository.updateProject(project);
    }
}
