package br.com.oxy.ssgt.application.taskcases;

import br.com.oxy.ssgt.application.gateways.TaskRepositoryApplication;


public class DeleteTask {

    private final TaskRepositoryApplication repository;

    public DeleteTask(TaskRepositoryApplication repository) {
        this.repository = repository;
    }

    public void deleteTask(Long id) {
        repository.deleteTask(id);
    }
}
