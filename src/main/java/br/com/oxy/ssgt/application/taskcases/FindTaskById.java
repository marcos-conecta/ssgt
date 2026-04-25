package br.com.oxy.ssgt.application.taskcases;

import br.com.oxy.ssgt.application.gateways.TaskRepositoryApplication;
import br.com.oxy.ssgt.domain.entities.task.Task;

public class FindTaskById {

    private final TaskRepositoryApplication repository;

    public FindTaskById(TaskRepositoryApplication repository) {
        this.repository = repository;
    }

    public Task findById(Long id) {
        return repository.findById(id);
    }
}
