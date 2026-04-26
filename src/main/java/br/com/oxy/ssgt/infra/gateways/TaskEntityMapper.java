package br.com.oxy.ssgt.infra.gateways;

import br.com.oxy.ssgt.domain.entities.task.Task;
import br.com.oxy.ssgt.infra.persistence.task.TaskEntity;

public class TaskEntityMapper {
    private final ProjectEntityMapper projectMapper;
    private final UserEntityMapper userMapper;

    public TaskEntityMapper(ProjectEntityMapper projectMapper, UserEntityMapper userMapper) {
        this.projectMapper = projectMapper;
        this.userMapper = userMapper;
    }
    public TaskEntity toEntity(Task task){

        TaskEntity taskEntity = new TaskEntity(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDeadline(),
                projectMapper.toEntity(task.getProject()),
                userMapper.toEntity(task.getAssignedUser())
        );
        taskEntity.setCreatedAt(task.getCreatedAt());
        taskEntity.setUpdatedAt(task.getUpdatedAt());

        return taskEntity;
    }

    public Task toDomain(TaskEntity taskEntity){
        Task task = new Task(
            taskEntity.getId(),
            taskEntity.getTitle(),
            taskEntity.getDescription(),
            taskEntity.getStatus(),
            taskEntity.getPriority(),
            taskEntity.getDeadline(),
            projectMapper.toDomain(taskEntity.getProject()),
            userMapper.toDomain(taskEntity.getAssignedUser())
        );
        task.setCreatedAt(taskEntity.getCreatedAt());
        task.setUpdatedAt(taskEntity.getUpdatedAt());

        return task;
    }
}
