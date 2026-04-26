package br.com.oxy.ssgt.infra.gateways;


import br.com.oxy.ssgt.application.gateways.TaskRepositoryApplication;
import br.com.oxy.ssgt.domain.entities.task.Task;
import br.com.oxy.ssgt.infra.execption.NotFoundException;
import br.com.oxy.ssgt.infra.persistence.task.TaskEntity;
import br.com.oxy.ssgt.infra.persistence.task.TaskPriority;
import br.com.oxy.ssgt.infra.persistence.task.TaskRepository;
import br.com.oxy.ssgt.infra.persistence.task.TaskStatus;
import br.com.oxy.ssgt.infra.persistence.task.specification.TaskSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;

public class TaskRepositoryJPA implements TaskRepositoryApplication {

    private final TaskRepository repository;
    private final TaskEntityMapper mapper;
    private final ProjectEntityMapper projectMapper;
    private final UserEntityMapper userMapper;

    public TaskRepositoryJPA(TaskRepository repository, TaskEntityMapper mapper, ProjectEntityMapper projectMapper, UserEntityMapper userMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.projectMapper = projectMapper;
        this.userMapper = userMapper;
    }

    @Override
    public Task registerTask(Task task) {
        TaskEntity entity = mapper.toEntity(task);
        TaskEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Page<Task> getAllTasks(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toDomain);
    }

    @Override
    public Task findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new NotFoundException("Task not found with id: " + id));
    }

    @Override
    public void deleteTask(Long id) {
        repository.deleteById(id);

    }

    @Override
    public Task updateTask(Task task) {
        TaskEntity entity = repository.findById(task.getId())
                .orElseThrow(() -> new NotFoundException("Task not found with id: " + task.getId()));

        entity.setId(task.getId());
        entity.setTitle(task.getTitle());
        entity.setDescription(task.getDescription());
        entity.setStatus(task.getStatus());
        entity.setPriority(task.getPriority());
        entity.setDeadline(task.getDeadline());
        entity.setProject(projectMapper.toEntity(task.getProject()));
        entity.setAssignedUser(userMapper.toEntity(task.getAssignedUser()));

        TaskEntity updated = repository.save(entity);

        return mapper.toDomain(updated);
    }

    @Override
    public Page<Task> findByProjectId(Long projectId, Pageable pageable) {
        return repository.findByProjectId(projectId, pageable)
                .map(mapper::toDomain);
    }

    @Override
    public long countInProgressByAssigneeUserId(Long assignedUserId) {
        return repository.countByAssignedUser_IdAndStatus(assignedUserId, TaskStatus.IN_PROGRESS);
    }

    @Override
    public Page<Task> findAllByCriteria(String text,
                                        TaskStatus status,
                                        TaskPriority priority,
                                        Long assignedUserId,
                                        LocalDateTime startDate,
                                        LocalDateTime endDate,
                                        Pageable pageable)
    {

        Specification<TaskEntity> specification = Specification.allOf(
                TaskSpecification.hasTextLike(text),
                TaskSpecification.hasStatus(status),
                TaskSpecification.hasPriority(priority),
                TaskSpecification.hasAssignedUserId(assignedUserId),
                TaskSpecification.createdAtBetween(startDate, endDate)
        );

        return repository.findAll(specification, pageable)
                .map(mapper::toDomain);
    }

    @Override
    public Map<TaskStatus, Long> countByStatusForProject(Long projectId) {
        Map<TaskStatus, Long> counts = new EnumMap<>(TaskStatus.class);

        for (TaskStatus status : TaskStatus.values()) {
           counts.put(
                status,
                repository.countByProject_IdAndStatus(projectId, status)
            );
        }
        return counts;

    }

    @Override
    public Map<TaskPriority, Long> countByPriorityForProject(Long projectId) {
        Map<TaskPriority, Long> counts = new EnumMap<>(TaskPriority.class);

        for (TaskPriority priority : TaskPriority.values()) {
            counts.put(
                    priority,
                    repository.countByProject_IdAndPriority(projectId, priority)
            );
        }
        return counts;
    }

}
