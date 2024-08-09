package org.example.taskmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagementsystem.dto.TaskDTO;
import org.example.taskmanagementsystem.model.Task;
import org.example.taskmanagementsystem.model.Users;
import org.example.taskmanagementsystem.repository.TaskRepository;
import org.example.taskmanagementsystem.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserService implements UserDetailsService {

    private final UsersRepository usersRepository;

    public List<TaskDTO> getAuthoredTasksByEmail(String email) {
        Users user = usersRepository.findByEmail(email).orElse(null);
        return user.getAuthoredTasks().stream().map(this::fromTaskToDto).toList();
    }

    public List<TaskDTO> getAssignedTasksByEmail(String email) {
        Users user = usersRepository.findByEmail(email).orElse(null);
        return user.getAssignedTasks().stream().map(this::fromTaskToDto).toList();
    }

    public Optional<Users> findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public Optional<Users> findById(Long id) {
        return usersRepository.findById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("user %s not found".formatted(username)));
    }
    public Task DtoToTask(TaskDTO taskDTO) {


        return Task.builder()
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .priority(taskDTO.getPriority())
                .status(taskDTO.getStatus())
                .author(this.findByEmail(taskDTO.getAuthor()).orElseThrow(() -> new IllegalArgumentException("User not found")))
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
