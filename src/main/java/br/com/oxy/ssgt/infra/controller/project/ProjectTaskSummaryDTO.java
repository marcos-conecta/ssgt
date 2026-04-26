package br.com.oxy.ssgt.infra.controller.project;

import br.com.oxy.ssgt.infra.persistence.task.TaskPriority;
import br.com.oxy.ssgt.infra.persistence.task.TaskStatus;

import java.util.Map;

public record ProjectTaskSummaryDTO(
        Map<TaskStatus, Long> byStatus,
        Map<TaskPriority, Long> byPriority
) {
}
