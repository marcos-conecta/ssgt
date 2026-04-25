package br.com.oxy.ssgt.application.taskcases;

import br.com.oxy.ssgt.application.gateways.TaskRepositoryApplication;
import br.com.oxy.ssgt.domain.entities.task.Task;

public class CreateTask {

    private final TaskRepositoryApplication repository;

    public CreateTask(TaskRepositoryApplication repository) {
        this.repository = repository;
    }

    public Task registerTask(Task task) {
        return repository.registerTask(task);
    }
}
