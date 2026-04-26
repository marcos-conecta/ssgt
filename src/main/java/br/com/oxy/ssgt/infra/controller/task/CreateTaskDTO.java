package br.com.oxy.ssgt.infra.controller.task;

import br.com.oxy.ssgt.infra.persistence.task.TaskPriority;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateTaskDTO(

        @Schema(description = "Task's title", example = "Implementar endpoint de criação de tarefa")
        @NotBlank
        String title,

        @Schema(description = "Task's description", example = "Criar um endpoint POST /tasks para permitir a criação de novas tarefas no sistema.")
        @NotBlank
        String description,

        @Schema(description = "Task's priority", example = "HIGH")
        @NotNull
        TaskPriority priority,

        @Schema(description = "Task's deadline timestamp", example = "2024-06-10T18:00:00")
        @NotNull
        LocalDateTime deadline,

        @Schema(description = "ID of the project to which the task belongs", example = "1")
        @NotNull
        Long projectId,

        @Schema(description = "ID of the user assigned to the task", example = "2")
        @NotNull
        Long assignedUserId
) {
}

