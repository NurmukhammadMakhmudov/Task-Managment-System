package org.example.taskmanagementsystem.service;

import org.example.taskmanagementsystem.dto.CommentDTO;
import org.example.taskmanagementsystem.model.Comments;
import org.example.taskmanagementsystem.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentsService {

    CommentRepository commentRepository;
    UserService userService;
    TasksService tasksService;

    @Autowired
    public CommentsService(CommentRepository commentRepository, UserService userService, TasksService tasksService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.tasksService = tasksService;
    }


    public CommentDTO save(CommentDTO comment, Long taskId, UserDetails userDetails) {
        if (comment.getComment().isBlank()) {
            throw new IllegalArgumentException("Comment cannot be blank");
        }
        var user = userService.findByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        var task = tasksService.getTaskById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        Comments comments = new Comments();
        comments.setComment(comment.getComment());
        comments.setAuthors(user);
        comments.setTask(task);
        commentRepository.save(comments);
        comment.setDate(LocalDateTime.now());
        comment.setAuthor(comments.getAuthors().getEmail());
        return comment;
    }

    public List<CommentDTO> findCommentsByTaskId(Long taskId) {

        return commentRepository.findByTaskId(taskId).stream()
                .map(this::toDTO)
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                            Collections.reverse(list);
                            return list;
                        }
                ));

    }


    public CommentDTO toDTO(Comments comments) {
        return CommentDTO.builder()
                .id(comments.getId())
                .comment(comments.getComment())
                .author(comments.getAuthors().getName())
                .date(comments.getCreationDate())
                .build();
    }
}
