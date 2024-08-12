package org.example.taskmanagementsystem.controller;


import org.example.taskmanagementsystem.dto.CommentDTO;
import org.example.taskmanagementsystem.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentsRestController {

    private final CommentsService commentsService;

    @Autowired
    public CommentsRestController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @PostMapping("{id}")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails, @RequestBody CommentDTO comment) {
        return ResponseEntity.ok(commentsService.save(comment,id, userDetails));
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(commentsService.findCommentsByTaskId(taskId));
    }
}
