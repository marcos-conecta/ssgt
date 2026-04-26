package br.com.oxy.ssgt.infra.persistence.task.specification;

import br.com.oxy.ssgt.infra.persistence.task.TaskEntity;
import br.com.oxy.ssgt.infra.persistence.task.TaskPriority;
import br.com.oxy.ssgt.infra.persistence.task.TaskStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class TaskSpecification {

    public static Specification<TaskEntity> hasStatus(TaskStatus status) {
        return (root, query, criteriaBuilder) ->
                status == null
                ? null
                : criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<TaskEntity> hasPriority(TaskPriority priority) {
        return (root, query, criteriaBuilder) ->
                priority == null
                ? null
                : criteriaBuilder.equal(root.get("priority"), priority);
    }

    public static Specification<TaskEntity> hasAssignedUserId(Long assignedUserId) {
        return (root, query, criteriaBuilder) ->
                assignedUserId == null
                ? null
                : criteriaBuilder.equal(root.get("assignedUser").get("id"), assignedUserId);
    }

    public static Specification<TaskEntity> createdAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate == null && endDate == null) {
                return null;
            }

            if (startDate != null && endDate != null) {
                return criteriaBuilder.between(root.get("createdAt"), startDate, endDate);
            }

            if (startDate != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), startDate);
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), endDate);
        };
    }
}
