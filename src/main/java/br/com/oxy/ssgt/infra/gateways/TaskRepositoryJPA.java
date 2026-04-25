package br.com.oxy.ssgt.infra.gateways;


import br.com.oxy.ssgt.application.gateways.TaskRepositoryApplication;
import br.com.oxy.ssgt.domain.entities.task.Task;
import br.com.oxy.ssgt.infra.persistence.task.TaskEntity;
import br.com.oxy.ssgt.infra.persistence.task.TaskRepository;
import br.com.oxy.ssgt.infra.persistence.task.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class TaskRepositoryJPA implements TaskRepositoryApplication {

    private final TaskRepository repository;
    private final TaskEntityMapper mapper;

    public TaskRepositoryJPA(TaskRepository repository, TaskEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Task registerTask(Task task) {
        TaskEntity entity = mapper.toEntity(task);
        repository.save(entity);
        return mapper.toDomain(entity);
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
                .orElse(null);
    }

    @Override
    public void deleteTask(Long id) {
        repository.deleteById(id);

    }

    @Override
    public Task updateTask(Task task) {
        TaskEntity entity = mapper.toEntity(task);
        repository.save(entity);
        return mapper.toDomain(entity);
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
}
