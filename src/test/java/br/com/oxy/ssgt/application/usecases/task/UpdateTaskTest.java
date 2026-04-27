package br.com.oxy.ssgt.application.usecases.task;

import br.com.oxy.ssgt.application.gateways.ProjectRepositoryApplication;
import br.com.oxy.ssgt.application.gateways.TaskRepositoryApplication;
import br.com.oxy.ssgt.domain.entities.project.Project;
import br.com.oxy.ssgt.domain.entities.task.Task;
import br.com.oxy.ssgt.domain.entities.user.User;
import br.com.oxy.ssgt.infra.execption.BusinessException;
import br.com.oxy.ssgt.infra.persistence.task.TaskPriority;
import br.com.oxy.ssgt.infra.persistence.task.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class UpdateTaskTest {

    private TaskRepositoryApplication taskRepository;
    private ProjectRepositoryApplication projectRepository;
    private UpdateTask updateTask;
    private User user;
    private Project project;

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepositoryApplication.class);
        projectRepository = mock(ProjectRepositoryApplication.class);
        updateTask = new UpdateTask(taskRepository, projectRepository);

        user = new User(
                1L,
                "João",
                "joao@email.com",
                "123456"
        );
        project = new Project(
                1L,
                "Projeto 1",
                "Descrição do Projeto 1",
                user,
                List.of(user)
        );
    }

    @Test
    void shouldNotAllowDoneTaskReturnTodo() {
        Task currentTask = new Task(
                1L,
                "Tarefa 1",
                "Descrição da Tarefa 1",
                TaskStatus.DONE,
                TaskPriority.HIGH,
                LocalDateTime.now().plusDays(3),
                project,
                user
        );

        Task newTask = new Task(
                1L,
                "Tarefa 1",
                "Descrição da Tarefa 1",
                TaskStatus.TODO,
                TaskPriority.HIGH,
                LocalDateTime.now().plusDays(3),
                project,
                user
        );

        when(taskRepository.findById(1L)).thenReturn(currentTask);
        when(projectRepository.isMember(1L, 1L)).thenReturn(true);

        assertThatThrownBy(() -> updateTask.execute(newTask, 1L))
                .isInstanceOf(BusinessException.class)
                .hasMessage("A task marked as DONE cannot be moved back to TODO.");

    }

    @Test
    void shouldNotAllowMemberToCloseCriticalTask() {
        Task currentTask = new Task(
                1L,
                "Tarefa 1",
                "Descrição da Tarefa 1",
                TaskStatus.IN_PROGRESS,
                TaskPriority.CRITICAL,
                LocalDateTime.now().plusDays(3),
                project,
                user
        );

        Task newTask = new Task(
                1L,
                "Tarefa 1",
                "Descrição da Tarefa 1",
                TaskStatus.DONE,
                TaskPriority.CRITICAL,
                LocalDateTime.now().plusDays(3),
                project,
                user
        );

        when(taskRepository.findById(1L)).thenReturn(currentTask);
        when(projectRepository.isMember(1L, 1L)).thenReturn(true);
        when(projectRepository.isAdmin(1L, 1L)).thenReturn(false);

        assertThatThrownBy(() -> updateTask.execute(newTask, 1L))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Only project admins can mark a CRITICAL task as DONE.");

        verify(taskRepository, never()).updateTask(any(Task.class));
    }

}
