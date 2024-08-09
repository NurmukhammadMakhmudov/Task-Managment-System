package org.example.taskmanagementsystem.service;


import lombok.RequiredArgsConstructor;
import org.example.taskmanagementsystem.Enums.StatusType;
import org.example.taskmanagementsystem.dto.StatusDTO;
import org.example.taskmanagementsystem.dto.TaskDTO;
import org.example.taskmanagementsystem.model.Task;
import org.example.taskmanagementsystem.model.Users;
import org.example.taskmanagementsystem.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TasksService {

    private final TaskRepository taskRepository;
    private final UserService userService;


    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream().map(this::fromTaskToDto).collect(Collectors.toList());
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public void deleteTaskById(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        } else throw new IllegalArgumentException("Task does not exist");
    }


    public TaskDTO save(TaskDTO taskDTO, UserDetails userDetails) {
        taskDTO.setAuthor(userDetails.getUsername());
        if (taskDTO.getStatus() == null)
            taskDTO.setStatus(StatusType.notStartedYet);
        var task = this.DtoToTask(taskDTO);
        taskRepository.save(task);
        return taskDTO;
    }


    public TaskDTO updateTask(Long taskId, TaskDTO updatedTask, UserDetails userDetails) {
        updatedTask.setAuthor(userDetails.getUsername());
        var task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task does not exist"));


        if ((task.getTitle() == null || !task.getTitle().equals(updatedTask.getTitle())) && updatedTask.getTitle() != null) {
            task.setTitle(updatedTask.getTitle());
        }
        if ((task.getDescription() == null || !task.getDescription().equals(updatedTask.getDescription())) && updatedTask.getDescription() != null) {
            task.setDescription(updatedTask.getDescription());
        }
        if ((task.getStatus() == null || !task.getStatus().equals(updatedTask.getStatus())) && updatedTask.getStatus() != null) {
            task.setStatus(updatedTask.getStatus());
        }
        if ((task.getPriority() == null || !task.getPriority().equals(updatedTask.getPriority())) && updatedTask.getPriority() != null) {
            task.setPriority(updatedTask.getPriority());
        }
        if ((task.getAssignee() == null || !task.getAssignee().equals(updatedTask.getAssignee()) && updatedTask.getAssignee() != null)) {
            task.setAssignee(userService.findByEmail(updatedTask.getAssignee()).orElseThrow(() -> new UsernameNotFoundException("Assignee Email not found")));
        }
        taskRepository.save(task);

        return this.fromTaskToDto(task);

    }

    public String assignTaskAssignee(Long taskId, Long assigneeId) {

        var task = taskRepository.findById(taskId);
        if (task.isEmpty()) {
            throw new IllegalArgumentException("Task does not exist");
        }
        Users user = null;
        if (userService.findById(assigneeId).isPresent()) {
            user = userService.findById(assigneeId).get();

            if (task.get().getAssignee() == null) {
                task.get().setAssignee(user);
            } else {
                if (!task.get().getAssignee().equals(user)) {
                    task.get().setAssignee(user);
                }
            }

        } else {
            throw new IllegalArgumentException("Assignee does not exist");
        }
        taskRepository.save(task.get());

        return "Task# %s assigned to %s".formatted(taskId, assigneeId);
    }


    public Task updateTaskStatus(Long taskId, StatusDTO status, String username) {
        var task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task does not exist"));
        if (!task.getAssignee().getEmail().equals(username)) {
            throw new IllegalArgumentException("You do not have permission to update this task");
        }
        task.setStatus(StatusType.valueOf(status.getStatus()));
        return taskRepository.save(task);

    }

    public Task DtoToTask(TaskDTO taskDTO) {


        return Task.builder()
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .priority(taskDTO.getPriority())
                .status(taskDTO.getStatus())
                .author(userService.findByEmail(taskDTO.getAuthor()).orElseThrow(() -> new IllegalArgumentException("User not found")))
                .build();
    }

    public TaskDTO fromTaskToDto(Task task) {
        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .priority(task.getPriority())
                .status(task.getStatus())
                .assignee(task.getAssignee() == null ? null : task.getAssignee().getEmail())
                .author(task.getAuthor().getEmail())
                .build();
    }


}
