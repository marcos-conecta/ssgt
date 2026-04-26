package br.com.oxy.ssgt.infra.controller.task;


import br.com.oxy.ssgt.infra.persistence.task.TaskPriority;
import br.com.oxy.ssgt.infra.persistence.task.TaskStatus;

import java.time.LocalDateTime;

public record TaskDTO(

        Long id,
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deadline,
        Long projectId,
        Long assignedUserId
) {
}

