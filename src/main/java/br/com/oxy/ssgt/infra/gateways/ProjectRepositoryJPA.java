package br.com.oxy.ssgt.infra.gateways;



import br.com.oxy.ssgt.application.gateways.ProjectRepositoryApplication;
import br.com.oxy.ssgt.domain.entities.project.Project;
import br.com.oxy.ssgt.infra.execption.NotFoundException;
import br.com.oxy.ssgt.infra.persistence.project.ProjectEntity;
import br.com.oxy.ssgt.infra.persistence.project.ProjectMemberRepository;
import br.com.oxy.ssgt.infra.persistence.project.ProjectRepository;
import br.com.oxy.ssgt.infra.persistence.project.ProjectRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ProjectRepositoryJPA implements ProjectRepositoryApplication {

    private final ProjectRepository repository;
    private final ProjectEntityMapper mapper;
    private final ProjectMemberRepository projectMemberRepository;
    public ProjectRepositoryJPA(ProjectRepository repository, ProjectEntityMapper mapper, ProjectMemberRepository projectMemberRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.projectMemberRepository = projectMemberRepository;
    }

    @Override
    public Project registerProject(Project task) {
        ProjectEntity entity = mapper.toEntity(task);
        repository.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public Page<Project> getAllProjects(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toDomain);
    }

    @Override
    public Project findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new NotFoundException("Project not found with id: " + id));
    }

    @Override
    public void deleteProject(Long id) {
        repository.deleteById(id);

    }

    @Override
    public Project updateProject(Project project) {
        ProjectEntity entity = mapper.toEntity(project);
        repository.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public boolean isAdmin(Long projectId, Long userId) {
        return projectMemberRepository.existsByProject_IdAndUser_IdAndRole(projectId, userId, ProjectRole.ADMIN);
    }

    @Override
    public boolean isMember(Long projectId, Long currentUserId) {
        return projectMemberRepository.existsByProject_IdAndUser_Id(projectId, currentUserId);
    }
}
