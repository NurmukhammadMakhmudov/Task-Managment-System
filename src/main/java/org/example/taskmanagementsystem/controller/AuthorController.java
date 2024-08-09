package org.example.taskmanagementsystem.controller;


import lombok.RequiredArgsConstructor;
import org.example.taskmanagementsystem.dto.CommentDTO;
import org.example.taskmanagementsystem.dto.TaskDTO;
import org.example.taskmanagementsystem.service.CommentsService;
import org.example.taskmanagementsystem.service.TasksService;
import org.example.taskmanagementsystem.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/author")
@RequiredArgsConstructor
public class AuthorController {

    private final TasksService tasksService;
    private final UserService userService;
    private final CommentsService commentsService;

    @GetMapping()
    public ResponseEntity<List<TaskDTO>> getAuthorTasks(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(userService.getAuthoredTasksByEmail(user.getUsername()));
    }

    @PostMapping("create-task")
    public ResponseEntity<TaskDTO> createTask(@AuthenticationPrincipal UserDetails userDetails, @RequestBody TaskDTO task) {
        return ResponseEntity.ok(tasksService.save(task, userDetails));
    }
    @PatchMapping("update-task/{id}")
    public TaskDTO updateTask(@AuthenticationPrincipal UserDetails userDetails,
                              @PathVariable Long id, @RequestBody TaskDTO task) {
        return tasksService.updateTask(id, task, userDetails);
    }


    @GetMapping("delete-task/{id}")
    public String deleteTask(@PathVariable Long id) {
        tasksService.deleteTaskById(id);
        return "Task deleted";
    }

    @GetMapping("/all-tasks")
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        return ResponseEntity.ok(tasksService.getAllTasks());
    }

    @PostMapping("/create-comment/{taskId}")
    public ResponseEntity<CommentDTO> createComment(
            @PathVariable Long taskId , @RequestBody CommentDTO comment, @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity.ok(commentsService.save(comment, taskId, userDetails));
    }

    @GetMapping("/task-comments/{taskId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(commentsService.findCommentsByTaskId(taskId));
    }

}
