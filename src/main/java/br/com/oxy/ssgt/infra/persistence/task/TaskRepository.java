package br.com.oxy.ssgt.infra.persistence.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TaskRepository extends JpaRepository<TaskEntity, Long>, JpaSpecificationExecutor <TaskEntity> {
    Page<TaskEntity> findByProjectId(Long projectId, Pageable pageable);
    long countByAssignedUser_IdAndStatus(Long assignedUserId, TaskStatus status);
}
