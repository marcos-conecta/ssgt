package br.com.oxy.ssgt.application.usecases.task;

import br.com.oxy.ssgt.application.gateways.TaskRepositoryApplication;
import br.com.oxy.ssgt.infra.controller.project.ProjectTaskSummaryDTO;

public class GetProjectTaskSummary {

    private final TaskRepositoryApplication repository;

    public GetProjectTaskSummary(TaskRepositoryApplication repository) {
        this.repository = repository;
    }

    public ProjectTaskSummaryDTO execute(Long projectId){
        return new ProjectTaskSummaryDTO(
                repository.countByStatusForProject(projectId),
                repository.countByPriorityForProject(projectId)
        );
    }
}
