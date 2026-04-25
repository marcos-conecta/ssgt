package br.com.oxy.ssgt.application.projectcases;

import br.com.oxy.ssgt.application.gateways.ProjectRepositoryApplication;
import br.com.oxy.ssgt.domain.entities.project.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ListProjects {

    private final ProjectRepositoryApplication repository;

    public ListProjects(ProjectRepositoryApplication repository) {
        this.repository = repository;
    }

    public Page<Project> getAllProjects(Pageable pageable) {
        return this.repository.getAllProjects(pageable);
    }
}
