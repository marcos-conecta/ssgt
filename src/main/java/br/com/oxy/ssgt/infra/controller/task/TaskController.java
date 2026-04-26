package br.com.oxy.ssgt.infra.controller.task;

import br.com.oxy.ssgt.application.projectcases.FindProjectById;
import br.com.oxy.ssgt.application.taskcases.*;
import br.com.oxy.ssgt.application.usecases.*;
import br.com.oxy.ssgt.domain.entities.project.Project;
import br.com.oxy.ssgt.domain.entities.task.Task;
import br.com.oxy.ssgt.domain.entities.user.User;
import br.com.oxy.ssgt.infra.security.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final CreateTask createTask;
    private final ListTasks listTasks;
    private final FindTaskById findTaskById;
    private final UpdateTask updateTask;
    private final DeleteTask deleteTask;
    private final FindProjectById findProjectById;
    private final FindUserById findUserById;
    private final ListTasksByProject listTaskByProject;
    private final SecurityUtils securityUtils;

    public TaskController(
            CreateTask createTask,
            ListTasks listTasks,
            FindTaskById findTaskById,
            UpdateTask updateTask,
            DeleteTask deleteTask,
            FindProjectById findProjectById,
            FindUserById findUserById,
            ListTasksByProject listTaskByProject,
            SecurityUtils securityUtils
    )
    {
        this.createTask = createTask;
        this.listTasks = listTasks;
        this.findTaskById = findTaskById;
        this.updateTask = updateTask;
        this.deleteTask = deleteTask;
        this.findProjectById = findProjectById;
        this.findUserById = findUserById;
        this.listTaskByProject = listTaskByProject;
        this.securityUtils = securityUtils;
    }

    @Operation(summary = "Create a new task", description = "Registers a new task in the system.")
    @PostMapping
    public TaskDTO createTask(@RequestBody @Valid CreateTaskDTO dto) {
        Project project = findProjectById.findById(dto.projectId());
        User assignedUser = findUserById.findById(dto.assignedUserId());
        Task task =  createTask.registerTask(
                new Task(
                        null,
                        dto.title(),
                        dto.description(),
                        null,
                        dto.priority(),
                        dto.deadline(),
                        project,
                        assignedUser)
        );

        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                task.getDeadline(),
                task.getProject().getId(),
                task.getAssignedUser().getId()
        );
    }

    @Operation(summary = "Get task by ID", description = "Retrieves a task by its unique identifier.")
    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable Long id) {
        Task task = findTaskById.findById(id);
        return new TaskDTO(task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                task.getDeadline(),
                task.getProject().getId(),
                task.getAssignedUser().getId()
        );
    }

    @Operation(summary = "Get all tasks", description = "Retrieves a paginated list of all tasks in the system.")
    @GetMapping
    public Page <TaskDTO> getAllTaskDTOS(@ParameterObject Pageable pageable) {
        return listTasks.getAllTasks(pageable)
                .map(task ->
                        new TaskDTO(
                                task.getId(),
                                task.getTitle(),
                                task.getDescription(),
                                task.getStatus(),
                                task.getPriority(),
                                task.getCreatedAt(),
                                task.getUpdatedAt(),
                                task.getDeadline(),
                                task.getProject().getId(),
                                task.getAssignedUser().getId())
                );
    }

    @Operation(summary = "Update an existing task", description = "Updates the details of an existing task in the system.")
    @PutMapping
    public TaskDTO updateTask(@RequestBody @Valid UpdateTaskDTO dto, Authentication authentication) {
        Project project = findProjectById.findById(dto.projectId());
        User assignedUser = findUserById.findById(dto.assignedUserId());

        Long currentUserId = securityUtils.getCurrentUserId(authentication);
        Task updateTask = this.updateTask.execute(
                new Task(
                        dto.id(),
                        dto.title(),
                        dto.description(),
                        dto.status(),
                        dto.priority(),
                        dto.deadline(),
                        project,
                        assignedUser
                ),
                currentUserId
        );
        return new TaskDTO(
                updateTask.getId(),
                updateTask.getTitle(),
                updateTask.getDescription(),
                updateTask.getStatus(),
                updateTask.getPriority(),
                updateTask.getCreatedAt(),
                updateTask.getUpdatedAt(),
                updateTask.getDeadline(),
                updateTask.getProject().getId(),
                updateTask.getAssignedUser().getId()
        );
    }

    @Operation(summary = "Delete a task", description = "Deletes a task from the system by its unique identifier.")
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        deleteTask.deleteTask(id);
    }

    @Operation(summary = "Get tasks by project ID", description = "Retrieves a paginated list of tasks associated with a specific project.")
    @GetMapping("/{projectId}/tasks")
    public Page<TaskDTO> getTasksByProject(
            @PathVariable Long projectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return listTaskByProject.execute(projectId, pageable).map(task ->
                new TaskDTO(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getStatus(),
                        task.getPriority(),
                        task.getCreatedAt(),
                        task.getUpdatedAt(),
                        task.getDeadline(),
                        task.getProject().getId(),
                        task.getAssignedUser().getId()
                )
        );
    }
}
