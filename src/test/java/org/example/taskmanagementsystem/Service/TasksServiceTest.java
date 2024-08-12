package org.example.taskmanagementsystem.Service;

import org.example.taskmanagementsystem.Enums.PriorityType;
import org.example.taskmanagementsystem.Enums.StatusType;
import org.example.taskmanagementsystem.dto.StatusDTO;
import org.example.taskmanagementsystem.dto.TaskDTO;
import org.example.taskmanagementsystem.model.Task;
import org.example.taskmanagementsystem.model.Users;
import org.example.taskmanagementsystem.repository.TaskRepository;
import org.example.taskmanagementsystem.service.TasksService;
import org.example.taskmanagementsystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TasksServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TasksService tasksService;


    @Test
    void testGetAllTasks() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("desc");
        task.setPriority(PriorityType.LOW);
        task.setStatus(StatusType.inProgress);
        Users users = new Users();
        users.setEmail("example@example.com");
        task.setAuthor(users);

        when(taskRepository.findAll()).thenReturn(List.of(task));

        List<TaskDTO> tasks = tasksService.getAllTasks();

        assertEquals("Test Task", tasks.get(0).getTitle());
    }

    @Test
    void testGetTaskById() {
        Task task = new Task();
        task.setId(1L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<Task> foundTask = tasksService.getTaskById(1L);

        assertTrue(foundTask.isPresent());
        assertEquals(1L, foundTask.get().getId());
    }

    @Test
    void testDeleteTaskById() {
        when(taskRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> tasksService.deleteTaskById(1L));
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteTaskById_TaskDoesNotExist() {
        when(taskRepository.existsById(1L)).thenReturn(false);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> tasksService.deleteTaskById(1L));
        assertEquals("Task does not exist", thrown.getMessage());
    }

    @Test
    void testSave() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("New Task");
        taskDTO.setAuthor("author@example.com");

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("author@example.com");
        when(userService.findByEmail("author@example.com")).thenReturn(Optional.of(new Users()));

        Task task = new Task();
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskDTO savedTaskDTO = tasksService.save(taskDTO, userDetails);

        assertEquals("New Task", savedTaskDTO.getTitle());
        assertEquals("author@example.com", savedTaskDTO.getAuthor());
    }

    @Test
    void testUpdateTask() {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setTitle("Old Title");
        existingTask.setDescription("Old Description");
        Users users = new Users();
        users.setEmail("author@example.com");
        existingTask.setAuthor(users);

        TaskDTO updatedTaskDTO = new TaskDTO();
        updatedTaskDTO.setTitle("New Title");
        updatedTaskDTO.setDescription("New Description");
        updatedTaskDTO.setAssignee("assignee@example.com");

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("assignee@example.com");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(userService.findByEmail("assignee@example.com")).thenReturn(Optional.of(new Users()));

        Task updatedTask = new Task();
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        TaskDTO result = tasksService.updateTask(1L, updatedTaskDTO, userDetails);

        assertEquals("New Title", result.getTitle());
        assertEquals("New Description", result.getDescription());
    }

    @Test
    void testAssignTaskAssignee() {
        Task task = new Task();
        task.setId(1L);
        task.setAssignee(null);

        Users assignee = new Users();
        assignee.setId(2L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(userService.findById(2L)).thenReturn(Optional.of(assignee));

        String result = tasksService.assignTaskAssignee(1L, 2L);

        assertEquals("Task# 1 assigned to 2", result);
        assertEquals(assignee, task.getAssignee());
    }

    @Test
    void testUpdateTaskStatus() {
        Task task = new Task();
        task.setId(1L);
        task.setAssignee(new Users());
        task.getAssignee().setEmail("assignee@example.com");
        task.setStatus(StatusType.notStartedYet);

        StatusDTO statusDTO = new StatusDTO();
        statusDTO.setStatus("inProgress");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task updatedTask = new Task();
        updatedTask.setStatus(StatusType.inProgress);
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        Task result = tasksService.updateTaskStatus(1L, statusDTO, "assignee@example.com");

        assertEquals(StatusType.inProgress, result.getStatus());
    }

    @Test
    void testUpdateTaskStatus_Forbidden() {
        Task task = new Task();
        task.setId(1L);
        task.setAssignee(new Users());
        task.getAssignee().setEmail("otheruser@example.com");

        StatusDTO statusDTO = new StatusDTO();
        statusDTO.setStatus("IN_PROGRESS");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> tasksService.updateTaskStatus(1L, statusDTO, "assignee@example.com"));
        assertEquals("You do not have permission to update this task", thrown.getMessage());
    }
}
