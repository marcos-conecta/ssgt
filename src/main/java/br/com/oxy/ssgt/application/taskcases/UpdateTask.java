package br.com.oxy.ssgt.application.taskcases;

import br.com.oxy.ssgt.application.gateways.ProjectRepositoryApplication;
import br.com.oxy.ssgt.application.gateways.TaskRepositoryApplication;
import br.com.oxy.ssgt.domain.entities.task.Task;
import br.com.oxy.ssgt.infra.persistence.task.TaskPriority;
import br.com.oxy.ssgt.infra.persistence.task.TaskStatus;

public class UpdateTask {

    private final TaskRepositoryApplication repository;
    private final ProjectRepositoryApplication projectRepository;

    public UpdateTask(TaskRepositoryApplication repository, ProjectRepositoryApplication projectRepository) {
        this.repository = repository;
        this.projectRepository = projectRepository;
    }

    public Task updateTask(Task newTask, Long currentUserId) {
        Task currentTask = repository.findById(newTask.getId());

        validateDoneCannotReturnToTodo(currentTask, newTask);

        validateCriticalOnlyAdminCanClose(currentTask, currentUserId);

        validateWipLimit(currentTask, newTask);

        return repository.updateTask(newTask);
    }

    private void validateDoneCannotReturnToTodo(Task currentTask, Task newTask) {
        if (currentTask.getStatus() == TaskStatus.DONE && newTask.getStatus() == TaskStatus.TODO) {
            throw new IllegalArgumentException("A task marked as DONE cannot be moved back to TODO.");
        }
    }

    private void validateCriticalOnlyAdminCanClose(Task newTask, Long currentUserId) {
        if (newTask.getPriority() == TaskPriority.CRITICAL && newTask.getStatus() == TaskStatus.DONE) {
            Long projectId = newTask.getProject().getId();

            boolean isAdmin = projectRepository.isAdmin(projectId, currentUserId);
            if (!isAdmin) {
                throw new IllegalArgumentException("Only project admins can mark a CRITICAL task as DONE.");
            }
        }
    }

    private void validateWipLimit(Task currentTask, Task newTask) {
        boolean isChangingToInProgress = currentTask.getStatus() != TaskStatus.IN_PROGRESS &&
                newTask.getStatus() == TaskStatus.IN_PROGRESS;

        if(isChangingToInProgress) {
            Long assignedUserId = newTask.getAssignedUser().getId();

            long totalInProgress = repository.countInProgressByAssigneeUserId(assignedUserId);
            if(totalInProgress >= 5) {
                throw new IllegalArgumentException("The assigned user already has 5 tasks in progress. Please reassign the task or complete some of the in-progress tasks.");
            }
        }
    }
}
