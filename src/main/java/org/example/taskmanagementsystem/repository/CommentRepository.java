package org.example.taskmanagementsystem.repository;

import org.example.taskmanagementsystem.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comments, Long> {
    List<Comments> findByTaskId(Long taskId);
}
