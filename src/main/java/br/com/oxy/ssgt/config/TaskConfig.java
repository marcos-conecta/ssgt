package br.com.oxy.ssgt.config;

import br.com.oxy.ssgt.application.gateways.ProjectRepositoryApplication;
import br.com.oxy.ssgt.application.gateways.TaskRepositoryApplication;
import br.com.oxy.ssgt.application.taskcases.*;
import br.com.oxy.ssgt.infra.gateways.ProjectEntityMapper;
import br.com.oxy.ssgt.infra.gateways.TaskEntityMapper;
import br.com.oxy.ssgt.infra.gateways.TaskRepositoryJPA;
import br.com.oxy.ssgt.infra.gateways.UserEntityMapper;
import br.com.oxy.ssgt.infra.persistence.task.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskConfig {

    @Bean
    CreateTask createTask(TaskRepositoryApplication repositoryTask) {
        return new CreateTask(repositoryTask);
    }

    @Bean
    ListTasks listTasks(TaskRepositoryApplication repositoryTask) {
        return new ListTasks(repositoryTask);
    }

    @Bean
    FindTaskById findTaskById(TaskRepositoryApplication repositoryTask) {
        return new FindTaskById(repositoryTask);
    }

    @Bean
    UpdateTask repositoryTask(TaskRepositoryApplication repositoryTask, ProjectRepositoryApplication repositoryProject) {
        return new UpdateTask(repositoryTask, repositoryProject);
    }

    @Bean
    DeleteTask deleteTask(TaskRepositoryApplication repositoryTask) {
        return new DeleteTask(repositoryTask);
    }

    @Bean
    TaskRepositoryJPA createTaskRepositoryJPA(TaskRepository repository, TaskEntityMapper mapper, ProjectEntityMapper projectMapper, UserEntityMapper userMapper) {
        return new TaskRepositoryJPA(repository, mapper, projectMapper, userMapper);
    }

    @Bean
    TaskEntityMapper returnTaskMapper(ProjectEntityMapper projectEntityMapper, UserEntityMapper userEntityMapper) {
        return new TaskEntityMapper(projectEntityMapper, userEntityMapper);
    }

}
