package org.example.taskmanagementsystem.repository;

import org.example.taskmanagementsystem.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAuthorEmail(String email);
    List<Task> findByAssigneeEmail(String email);
}
