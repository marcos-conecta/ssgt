package br.com.oxy.ssgt.infra.controller.project;

import br.com.oxy.ssgt.application.projectcases.*;
import br.com.oxy.ssgt.application.taskcases.GetProjectTaskSummary;
import br.com.oxy.ssgt.application.usecases.FindUserById;
import br.com.oxy.ssgt.domain.entities.project.Project;
import br.com.oxy.ssgt.domain.entities.user.User;
import br.com.oxy.ssgt.infra.security.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final CreateProject createProject;
    private final ListProjects listProjects;
    private final FindProjectById findProjectById;
    private final UpdateProject updateProject;
    private final DeleteProject deleteProject;
    private final FindUserById findUserById;
    private final SecurityUtils securityUtils;
    private final GetProjectTaskSummary getProjectTaskSummary;

    public ProjectController(
            CreateProject createProject,
            ListProjects listProjects,
            FindProjectById findProjectById,
            UpdateProject updateProject,
            DeleteProject deleteProject,
            FindUserById findUserById,
            SecurityUtils securityUtils,
            GetProjectTaskSummary getProjectTaskSummary
    )
    {
        this.createProject = createProject;
        this.listProjects = listProjects;
        this.findProjectById = findProjectById;
        this.updateProject = updateProject;
        this.deleteProject = deleteProject;
        this.findUserById = findUserById;
        this.securityUtils = securityUtils;
        this.getProjectTaskSummary = getProjectTaskSummary;

    }

    @Operation(summary = "Create a new project", description = "Registers a new project in the system.")
    @PostMapping
    public ProjectDTO createProject(@RequestBody @Valid CreateProjectDTO dto) {
        User owner = findUserById.findById(dto.ownerId());

        List<User> members = dto.members()
                .stream()
                .map(findUserById::findById)
                .toList();

        Project project =  createProject.registerProject
                (new Project(
                        null,
                        dto.name(),
                        dto.description(),
                        owner,
                        members)
                );

        return new ProjectDTO(project);
    }

    @Operation(summary = "Get project by ID", description = "Retrieves a project by its unique ID.")
    @GetMapping("/{id}")
    public ProjectDTO getProjectById(@PathVariable Long id) {
        Project project = findProjectById.findById(id);
        return new ProjectDTO(project);
    }

    @Operation(summary = "Get all projects", description = "Retrieves a paginated list of all projects in the system.")
    @GetMapping
    public Page <ProjectDTO> getAllProjectDTOS(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return listProjects.getAllProjects(pageable).map(ProjectDTO::new);
    }

    @Operation(summary = "Update project", description = "Updates the details of an existing project.")
    @PutMapping
    public ProjectDTO updateProject(@RequestBody @Valid ProjectDTO dto, Authentication authentication) {
        User owner = findUserById.findById(dto.ownerId());


        Long currentUserId = securityUtils.getCurrentUserId(authentication);

        List<User> members = dto.members()
                .stream()
                .map(findUserById::findById)
                .toList();

        Project project = new Project(
                dto.id(),
                dto.name(),
                dto.description(),
                owner,
                members
        );

        Project projectUpdate = updateProject.updateProject(currentUserId, project);

        return new ProjectDTO(projectUpdate);
    }

    @Operation(summary = "Delete project", description = "Deletes a project from the system by its unique ID.")
    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        deleteProject.deleteProject(id);
    }

    @Operation(summary = "Get project task summary", description = "Retrieves a summary of tasks for a specific project, including counts of tasks by status and priority.")
    @GetMapping("/{projectId}/tasks/summary")
    public ProjectTaskSummaryDTO getProjectTaskSummary(@PathVariable Long projectId) {
        return getProjectTaskSummary.execute(projectId);
    }

}
