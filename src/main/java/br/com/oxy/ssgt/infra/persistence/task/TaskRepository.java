package br.com.oxy.ssgt.infra.persistence.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    Page<TaskEntity> findByProjectId(Long projectId, Pageable pageable);
    long countByAssignedUser_IdAndStatus(Long assignedUserId, TaskStatus status);
}
