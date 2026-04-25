package br.com.oxy.ssgt.application.taskcases;

import br.com.oxy.ssgt.application.gateways.TaskRepositoryApplication;
import br.com.oxy.ssgt.domain.entities.task.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ListTasks {

    private final TaskRepositoryApplication repository;

    public ListTasks(TaskRepositoryApplication repository) {
        this.repository = repository;
    }

    public Page<Task> getAllTasks(Pageable pageable) {
        return this.repository.getAllTasks(pageable);
    }
}
