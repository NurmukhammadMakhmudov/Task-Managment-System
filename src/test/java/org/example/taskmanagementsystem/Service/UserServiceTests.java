package org.example.taskmanagementsystem.Service;

import org.example.taskmanagementsystem.Enums.PriorityType;
import org.example.taskmanagementsystem.Enums.StatusType;
import org.example.taskmanagementsystem.dto.TaskDTO;
import org.example.taskmanagementsystem.model.Task;
import org.example.taskmanagementsystem.model.Users;
import org.example.taskmanagementsystem.repository.UsersRepository;
import org.example.taskmanagementsystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UserService userService;


    @Test
    void testGetAuthoredTasksByEmail() {
        Users user = new Users();
        user.setEmail("author@example.com");
        Task task = new Task();
        task.setTitle("Sample Task");
        task.setAuthor(user);
        user.setAuthoredTasks(List.of(task));

        when(usersRepository.findByEmail("author@example.com")).thenReturn(Optional.of(user));

        List<TaskDTO> tasks = userService.getAuthoredTasksByEmail("author@example.com");

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals("Sample Task", tasks.get(0).getTitle());
        verify(usersRepository, times(1)).findByEmail("author@example.com");
    }

    @Test
    void testGetAssignedTasksByEmail() {
        Users user = new Users();
        user.setEmail("author@example.com");
        Task task = new Task();
        task.setTitle("Sample Task");
        task.setAuthor(user);
        user.setAssignedTasks(List.of(task));

        when(usersRepository.findByEmail("author@example.com")).thenReturn(Optional.of(user));

        List<TaskDTO> tasks = userService.getAssignedTasksByEmail("author@example.com");

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals("Sample Task", tasks.get(0).getTitle());
        verify(usersRepository, times(1)).findByEmail("author@example.com");
    }

    @Test
    public void testFindByEmail() {
        String email = "example@example.com";
        Users users = new Users();
        users.setEmail(email);
        when(usersRepository.findByEmail(email)).thenReturn(Optional.of(users));

        Optional<Users> retrivedUser = userService.findByEmail(email);
        assertTrue(retrivedUser.isPresent());
        assertEquals(email, retrivedUser.get().getEmail());
    }

    @Test
    void testLoadUserByUsername() {
        Users user = new Users();
        user.setEmail("test@example.com");

        when(usersRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername("test@example.com");

        assertNotNull(userDetails);
        assertEquals("test@example.com", userDetails.getUsername());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(usersRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("nonexistent@example.com");
        });
    }

    @Test
    void testDtoToTask() {
        TaskDTO taskDTO = TaskDTO.builder()
                .title("Test Task")
                .description("Task Description")
                .priority(PriorityType.HIGH)
                .status(StatusType.inProgress)
                .author("author@example.com")
                .build();

        Users author = new Users();
        author.setEmail("author@example.com");

        when(usersRepository.findByEmail("author@example.com")).thenReturn(Optional.of(author));

        Task task = userService.DtoToTask(taskDTO);

        assertNotNull(task);
        assertEquals("Test Task", task.getTitle());
        assertEquals("Task Description", task.getDescription());
        assertEquals(PriorityType.HIGH, task.getPriority());
        assertEquals(StatusType.inProgress, task.getStatus());
        assertEquals("author@example.com", task.getAuthor().getEmail());
    }

}
