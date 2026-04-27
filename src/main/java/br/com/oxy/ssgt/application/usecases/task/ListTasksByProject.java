package br.com.oxy.ssgt.application.usecases.task;

import br.com.oxy.ssgt.application.gateways.TaskRepositoryApplication;
import br.com.oxy.ssgt.domain.entities.task.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ListTasksByProject {

    private final TaskRepositoryApplication repository;

    public ListTasksByProject(TaskRepositoryApplication repository) {
        this.repository = repository;
    }

    public Page<Task> execute(Long projectId, Pageable pageable) {
        return repository.findByProjectId(projectId, pageable);
    }

}
