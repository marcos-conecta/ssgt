package br.com.oxy.ssgt.application.gateways;


import br.com.oxy.ssgt.domain.entities.project.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectRepositoryApplication {
    Project registerProject(Project project);

    Page<Project> getAllProjects(Pageable pageable);

    Project findById(Long id);

    void deleteProject(Long id);

    Project updateProject(Project project);

    boolean isAdmin(Long projectId, Long userId);

    boolean isMember(Long projectId, Long currentUserId);
}
