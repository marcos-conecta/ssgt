package br.com.oxy.ssgt.application.projectcases;

import br.com.oxy.ssgt.application.gateways.ProjectRepositoryApplication;
import br.com.oxy.ssgt.domain.entities.project.Project;

public class FindProjectById {

    private final ProjectRepositoryApplication repository;

    public FindProjectById(ProjectRepositoryApplication repository) {
        this.repository = repository;
    }

    public Project findById(Long id) {
        return repository.findById(id);
    }
}
