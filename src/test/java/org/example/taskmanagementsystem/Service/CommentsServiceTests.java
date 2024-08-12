package org.example.taskmanagementsystem.Service;

import org.example.taskmanagementsystem.dto.CommentDTO;
import org.example.taskmanagementsystem.model.Comments;
import org.example.taskmanagementsystem.model.Task;
import org.example.taskmanagementsystem.model.Users;
import org.example.taskmanagementsystem.repository.CommentRepository;
import org.example.taskmanagementsystem.service.CommentsService;
import org.example.taskmanagementsystem.service.TasksService;
import org.example.taskmanagementsystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CommentsServiceTests {

    @InjectMocks
    private CommentsService commentsService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserService userService;

    @Mock
    private TasksService tasksService;

    @Test
    void testSaveComment() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setComment("Test Comment");
        Long taskId = 1L;
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("test@example.com");

        Users user = new Users();
        user.setEmail("test@example.com");

        Task task = new Task();

        when(userService.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(tasksService.getTaskById(taskId)).thenReturn(Optional.of(task));

        CommentDTO savedComment = commentsService.save(commentDTO, taskId, userDetails);

        assertNotNull(savedComment);
        assertEquals("Test Comment", savedComment.getComment());
        verify(commentRepository).save(any(Comments.class));
    }
}
