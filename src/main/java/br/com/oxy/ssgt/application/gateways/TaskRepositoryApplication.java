package br.com.oxy.ssgt.application.gateways;

import br.com.oxy.ssgt.domain.entities.task.Task;
import br.com.oxy.ssgt.infra.persistence.task.TaskEntity;
import br.com.oxy.ssgt.infra.persistence.task.TaskPriority;
import br.com.oxy.ssgt.infra.persistence.task.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Map;

public interface TaskRepositoryApplication {

    Task registerTask(Task task);

    Page<Task> getAllTasks(Pageable pageable);

    Task findById(Long id);

    void deleteTask(Long id);

    Task updateTask(Task task);

    Page<Task> findByProjectId(Long projectId,Pageable pageable);

    long countInProgressByAssigneeUserId(Long assignedUserId);

    Page<Task> findAllByCriteria(String text,
                                 TaskStatus status,
                                 TaskPriority priority,
                                 Long assignedUserId,
                                 LocalDateTime startDate,
                                 LocalDateTime endDate,
                                 Pageable pageable
    );

    Map<TaskStatus, Long> countByStatusForProject(Long projectId);

    Map<TaskPriority , Long> countByPriorityForProject(Long projectId);
}
