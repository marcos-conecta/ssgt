package br.com.oxy.ssgt.infra.controller.task;

import br.com.oxy.ssgt.domain.entities.task.Task;
import br.com.oxy.ssgt.infra.persistence.task.TaskPriority;
import br.com.oxy.ssgt.infra.persistence.task.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record TaskDTO(
        @Schema(description = "Task's unique identifier", example = "1")
        Long id,

        @Schema(description = "Task's title", example = "Implementar endpoint de criação de tarefa")
        String title,

        @Schema(description = "Task's description", example = "Criar um endpoint POST /tasks para permitir a criação de novas tarefas no sistema.")
        String description,

        @Schema(description = "Task's status", example = "TODO")
        TaskStatus status,

        @Schema(description = "Task's priority", example = "HIGH")
        TaskPriority priority,

        @Schema(description = "Task's creation timestamp", example = "2024-06-01T12:00:00")
        LocalDateTime createdAt,

        @Schema(description = "Task's last update timestamp", example = "2024-06-02T15:30:00")
        LocalDateTime updatedAt,

        @Schema(description = "Task's deadline timestamp", example = "2024-06-10T18:00:00")
        LocalDateTime deadline,

        @Schema(description = "ID of the project to which the task belongs", example = "1")
        Long projectId,

        @Schema(description = "ID of the user assigned to the task", example = "2")
        Long assignedUserId
) {
    public TaskDTO(Task task){
        this(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                task.getDeadline(),
                task.getProject().getId(),
                task.getAssignedUser().getId());
    }
}

