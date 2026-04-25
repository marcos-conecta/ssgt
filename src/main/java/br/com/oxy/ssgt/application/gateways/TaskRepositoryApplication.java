package br.com.oxy.ssgt.application.gateways;

import br.com.oxy.ssgt.domain.entities.task.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskRepositoryApplication {

    Task registerTask(Task task);

    Page<Task> getAllTasks(Pageable pageable);

    Task findById(Long id);

    void deleteTask(Long id);

    Task updateTask(Task task);

    Page<Task> findByProjectId(Long projectId,Pageable pageable);

    long countInProgressByAssigneeUserId(Long assignedUserId);
}
