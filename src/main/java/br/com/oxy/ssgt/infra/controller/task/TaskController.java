package br.com.oxy.ssgt.infra.controller.task;

import br.com.oxy.ssgt.application.usecases.project.FindProjectById;
import br.com.oxy.ssgt.application.usecases.task.*;
import br.com.oxy.ssgt.application.usecases.user.FindUserById;
import br.com.oxy.ssgt.domain.entities.project.Project;
import br.com.oxy.ssgt.domain.entities.task.Task;
import br.com.oxy.ssgt.domain.entities.user.User;
import br.com.oxy.ssgt.infra.persistence.task.TaskPriority;
import br.com.oxy.ssgt.infra.persistence.task.TaskStatus;
import br.com.oxy.ssgt.infra.security.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;


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
    public ResponseEntity<TaskDTO> createTask(@RequestBody @Valid CreateTaskDTO dto) {
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
        URI location = URI.create("/tasks/" + task.getId());
        return ResponseEntity.created(location).body(new TaskDTO(task));
    }

    @Operation(summary = "Get task by ID", description = "Retrieves a task by its unique identifier.")
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long taskId) {
        Task task = findTaskById.findById(taskId);
        return ResponseEntity.ok(new TaskDTO(task));
    }

    @Operation(summary = "Get all tasks with optional filters", description = "Retrieves a paginated list of tasks with optional filtering by status, priority, assigned user, and date range.")
    @GetMapping
    public ResponseEntity<Page <TaskDTO>> getAllTaskDTOS(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) TaskStatus taskStatus,
            @RequestParam(required = false) TaskPriority taskPriority,
            @RequestParam(required = false) Long assignedUserId,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(listTasks.getAllTasks(text, taskStatus, taskPriority, assignedUserId, startDate, endDate, pageable)
                .map(TaskDTO::new));

    }

    @Operation(summary = "Update an existing task", description = "Updates the details of an existing task in the system.")
    @PutMapping
    public ResponseEntity<TaskDTO> updateTask(@RequestBody @Valid UpdateTaskDTO dto, Authentication authentication) {
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
        return ResponseEntity.ok(new TaskDTO(updateTask));
    }

    @Operation(summary = "Delete a task", description = "Deletes a task from the system by its unique identifier.")
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        deleteTask.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get tasks by project ID", description = "Retrieves a paginated list of tasks associated with a specific project.")
    @GetMapping("/{projectId}/tasks")
    public ResponseEntity<Page<TaskDTO>> getTasksByProject(
            @PathVariable Long projectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(listTaskByProject.execute(projectId, pageable).map(TaskDTO::new));
    }
}
