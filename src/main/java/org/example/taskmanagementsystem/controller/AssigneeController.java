package org.example.taskmanagementsystem.controller;


import lombok.RequiredArgsConstructor;
import org.example.taskmanagementsystem.dto.CommentDTO;
import org.example.taskmanagementsystem.dto.StatusDTO;
import org.example.taskmanagementsystem.dto.TaskDTO;
import org.example.taskmanagementsystem.model.Task;
import org.example.taskmanagementsystem.service.CommentsService;
import org.example.taskmanagementsystem.service.TasksService;
import org.example.taskmanagementsystem.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/assignee")
@RequiredArgsConstructor
public class AssigneeController {

    private final CommentsService  commentsService;
    private final TasksService tasksService;
    private final UserService userService;

    @GetMapping("/assigned-tasks")
    public ResponseEntity<List<TaskDTO>> getAssignedTasks(@AuthenticationPrincipal UserDetails user)
    {
        return ResponseEntity.ok(userService.getAssignedTasksByEmail(user.getUsername()));
    }

    @PatchMapping("/status-update/{taskId}")
    public ResponseEntity<Task> updateStatus(
            @PathVariable Long taskId , @RequestBody StatusDTO status, @AuthenticationPrincipal UserDetails user)
    {
        return ResponseEntity.ok(tasksService.updateTaskStatus(taskId, status, user.getUsername()));
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
