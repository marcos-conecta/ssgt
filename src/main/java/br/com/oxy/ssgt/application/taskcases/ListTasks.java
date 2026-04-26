package br.com.oxy.ssgt.application.taskcases;

import br.com.oxy.ssgt.application.gateways.TaskRepositoryApplication;
import br.com.oxy.ssgt.domain.entities.task.Task;
import br.com.oxy.ssgt.infra.persistence.task.TaskPriority;
import br.com.oxy.ssgt.infra.persistence.task.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public class ListTasks {

    private final TaskRepositoryApplication repository;

    public ListTasks(TaskRepositoryApplication repository) {
        this.repository = repository;
    }

    public Page<Task> getAllTasks(String text,
                                  TaskStatus taskStatus,
                                  TaskPriority taskPriority,
                                  Long assignedUserId,
                                  LocalDateTime startDate,
                                  LocalDateTime endDate,
                                  Pageable pageable
    ) {
        return this.repository.findAllByCriteria(text,taskStatus, taskPriority, assignedUserId, startDate, endDate, pageable);
    }
}
