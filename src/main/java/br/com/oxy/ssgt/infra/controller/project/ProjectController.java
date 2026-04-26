package br.com.oxy.ssgt.infra.controller.project;

import br.com.oxy.ssgt.application.projectcases.*;
import br.com.oxy.ssgt.application.taskcases.ListTasksByProject;
import br.com.oxy.ssgt.application.usecases.FindUserById;
import br.com.oxy.ssgt.domain.entities.project.Project;
import br.com.oxy.ssgt.domain.entities.user.User;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
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

    public ProjectController(
            CreateProject createProject,
            ListProjects listProjects,
            FindProjectById findProjectById,
            UpdateProject updateProject,
            DeleteProject deleteProject,
            FindUserById findUserById
    )
    {
        this.createProject = createProject;
        this.listProjects = listProjects;
        this.findProjectById = findProjectById;
        this.updateProject = updateProject;
        this.deleteProject = deleteProject;
        this.findUserById = findUserById;

    }

    @Operation(summary = "Create a new project", description = "Registers a new project in the system.")
    @PostMapping
    public ProjectDTO createProject(@RequestBody ProjectDTO dto) {
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

        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getOwner().getId(),
                project.getMembers()
                        .stream()
                        .map(User::getId).toList()
        );
    }

    @Operation(summary = "Get project by ID", description = "Retrieves a project by its unique ID.")
    @GetMapping("/{id}")
    public ProjectDTO getProjectById(@PathVariable Long id) {
        Project project = findProjectById.findById(id);
        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getOwner().getId(),
                project.getMembers()
                .stream()
                        .map(User::getId).toList());
    }

    @Operation(summary = "Get all projects", description = "Retrieves a paginated list of all projects in the system.")
    @GetMapping
    public Page <ProjectDTO> getAllProjectDTOS(@ParameterObject Pageable pageable) {
        return listProjects.getAllProjects(pageable)
                .map(project -> new ProjectDTO(
                                project.getId(),
                                project.getName(),
                                project.getDescription(),
                                project.getOwner().getId(),
                                project.getMembers()
                                .stream().map(User::getId).toList()
                        )
                );
    }

    @Operation(summary = "Update project", description = "Updates the details of an existing project.")
    @PutMapping
    public ProjectDTO updateProject(@RequestBody @Valid ProjectDTO dto, Authentication authentication) {
        User owner = findUserById.findById(dto.ownerId());

        Jwt jwt = (Jwt) authentication.getPrincipal();
        Long currentUserId = jwt.getClaim("userId");

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

}
