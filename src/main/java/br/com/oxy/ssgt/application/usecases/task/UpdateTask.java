package br.com.oxy.ssgt.application.usecases.task;

import br.com.oxy.ssgt.application.gateways.ProjectRepositoryApplication;
import br.com.oxy.ssgt.application.gateways.TaskRepositoryApplication;
import br.com.oxy.ssgt.domain.entities.task.Task;
import br.com.oxy.ssgt.infra.execption.BusinessException;
import br.com.oxy.ssgt.infra.persistence.task.TaskPriority;
import br.com.oxy.ssgt.infra.persistence.task.TaskStatus;

public class UpdateTask {

    private final TaskRepositoryApplication repository;
    private final ProjectRepositoryApplication projectRepository;

    public UpdateTask(TaskRepositoryApplication repository, ProjectRepositoryApplication projectRepository) {
        this.repository = repository;
        this.projectRepository = projectRepository;
    }

    public Task execute(Task newTask, Long currentUserId) {
        Task currentTask = repository.findById(newTask.getId());
        Long projectId = newTask.getProject().getId();
        Long assignedUserId = currentTask.getAssignedUser().getId();

        validateCurrentUserIsProjectMember(projectId, currentUserId);

        validateAssignedUserIsProjectMember(projectId, assignedUserId);

        validateDoneCannotReturnToTodo(currentTask, newTask);

        validateCriticalOnlyAdminCanClose(currentTask, newTask, currentUserId);

        validateWipLimit(currentTask, newTask);

        return repository.updateTask(newTask);
    }

    private void validateCurrentUserIsProjectMember(Long projectId, Long currentUserId) {
        if(!projectRepository.isMember(projectId, currentUserId)) {
            throw new BusinessException("User must be a member of the project to manipulate tasks");
        }
    }

    private void validateAssignedUserIsProjectMember(Long projectId, Long currentUserId) {
        if(!projectRepository.isMember(projectId, currentUserId)) {
            throw new BusinessException("User must be a member of the project to update tasks.");
        }
    }

    private void validateDoneCannotReturnToTodo(Task currentTask, Task newTask) {
        if (currentTask.getStatus() == TaskStatus.DONE && newTask.getStatus() == TaskStatus.TODO) {
            throw new BusinessException("A task marked as DONE cannot be moved back to TODO.");
        }
    }

    private void validateCriticalOnlyAdminCanClose(Task currentTask, Task newTask, Long currentUserId) {
        Long projectId = currentTask.getProject().getId();

        if (currentTask.getPriority() == TaskPriority.CRITICAL && newTask.getStatus() == TaskStatus.DONE) {

            boolean isAdmin = projectRepository.isAdmin(projectId, currentUserId);
            if (!isAdmin) {
                throw new BusinessException("Only project admins can mark a CRITICAL task as DONE.");
            }
        }
    }

    private void validateWipLimit(Task currentTask, Task newTask) {
        boolean isChangingToInProgress = currentTask.getStatus() != TaskStatus.IN_PROGRESS &&
                newTask.getStatus() == TaskStatus.IN_PROGRESS;

        boolean reassignedWhileInProgress = currentTask.getStatus() == TaskStatus.IN_PROGRESS &&
                newTask.getStatus() == TaskStatus.IN_PROGRESS &&
                !currentTask.getAssignedUser().getId()
                        .equals(newTask.getAssignedUser().getId());

        if(isChangingToInProgress || reassignedWhileInProgress){

            Long assignedUserId = newTask.getAssignedUser().getId();

            long totalInProgress = repository.countInProgressByAssigneeUserId(assignedUserId);

            if(totalInProgress >= 5) {
                throw new BusinessException("The assigned user already has 5 tasks in progress. Please reassign the task or complete some of the in-progress tasks.");
            }
        }
    }
}
