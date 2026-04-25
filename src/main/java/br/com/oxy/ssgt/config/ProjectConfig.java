package br.com.oxy.ssgt.config;

import br.com.oxy.ssgt.application.gateways.ProjectRepositoryApplication;
import br.com.oxy.ssgt.application.gateways.TaskRepositoryApplication;
import br.com.oxy.ssgt.application.projectcases.*;
import br.com.oxy.ssgt.application.taskcases.ListTasksByProject;
import br.com.oxy.ssgt.infra.gateways.ProjectEntityMapper;
import br.com.oxy.ssgt.infra.gateways.ProjectRepositoryJPA;
import br.com.oxy.ssgt.infra.gateways.UserEntityMapper;
import br.com.oxy.ssgt.infra.persistence.project.ProjectMemberRepository;
import br.com.oxy.ssgt.infra.persistence.project.ProjectRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {

    @Bean
    CreateProject createProject(ProjectRepositoryApplication repositoryProject) {
        return new CreateProject(repositoryProject);
    }

    @Bean
    ListProjects listProjects(ProjectRepositoryApplication repositoryProject) {
        return new ListProjects(repositoryProject);
    }

    @Bean
    FindProjectById findProjectById(ProjectRepositoryApplication repositoryProject) {
        return new FindProjectById(repositoryProject);
    }

    @Bean
    UpdateProject repositoryProject(ProjectRepositoryApplication repositoryProject) {
        return new UpdateProject(repositoryProject);
    }

    @Bean
    DeleteProject deleteProject(ProjectRepositoryApplication repositoryProject) {
        return new DeleteProject(repositoryProject);
    }

    @Bean
    ProjectRepositoryJPA createProjectRepositoryJPA(ProjectRepository repository, ProjectEntityMapper mapper, ProjectMemberRepository projectMemberRepository) {
        return new ProjectRepositoryJPA(repository, mapper, projectMemberRepository);
    }

    @Bean
    ProjectEntityMapper returnProjectMapper(UserEntityMapper userMapper) {
        return new ProjectEntityMapper(userMapper);
    }

    @Bean
    ListTasksByProject listTasksByProject(TaskRepositoryApplication taskRepositoryApplication){
        return new ListTasksByProject(taskRepositoryApplication);
    }
}
