//package org.example.taskmanagementsystem.service;
//
//import org.example.taskmanagementsystem.model.Comments;
//import org.example.taskmanagementsystem.model.Task;
//import org.example.taskmanagementsystem.repository.CommentRepository;
//import org.example.taskmanagementsystem.repository.TaskRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class AuthorService {
//
//    private final AuthorRepository authorRepository;
//    private final TaskRepository taskRepository;
//    private final CommentRepository commentRepository;
//
//    @Autowired
//    public AuthorService(AuthorRepository authorRepository, TaskRepository taskRepository, CommentRepository commentRepository) {
//        this.authorRepository = authorRepository;
//        this.taskRepository = taskRepository;
//        this.commentRepository = commentRepository;
//    }
//
//    public List<Task> getAllTasksByAuthorId(long authorId) {
//        var authors = authorRepository.findById(authorId).orElseThrow(() -> new RuntimeException("Author not found"));
//        return authors.getTasks();
//    }
////    public List<Comments> getAllCommentsByAuthorId(long authorId) {
////        var authors = authorRepository.findById(authorId).orElseThrow(() -> new RuntimeException("Author not found"));
////        return authors.getComments();
////    }
//
//    public String createTask(Long authors, Task task) {
//
//        task.setAuthor( authorRepository.findById(authors).orElseThrow(() -> new RuntimeException("Author not found")));
//        taskRepository.save(task);
//        return "Task created";
//    }
//
//    public String updateTask(Authors authors, long taskId, Task newTask) {
//        var task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
//        if (task.getAuthor() != authors) {
//            throw new RuntimeException("Authors do not match");
//        }
//        task.setAssignee(newTask.getAssignee());
//        task.setPriority(newTask.getPriority());
//        task.setDescription(newTask.getDescription());
//        task.setStatus(newTask.getStatus());
//        task.setTitle(newTask.getTitle());
//        taskRepository.save(task);
//        return "Task updated";
//    }
//
//    public String deleteTask(long taskId) {
//        var task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
//        taskRepository.delete(task);
//        return "Task deleted";
//    }
//
//    public List<Comments> getAllCommentsByTaskId(long taskId) {
//        var task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
//        return task.getComments();
//    }
//    public String addComment(long taskId, String comment) {
//        var task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
//        Comments comments = new Comments();
//        comments.setComment(comment);
//        comments.setTask(task);
//        commentRepository.save(comments);
//        return "Comment added";
//    }
//
//
//
//
//
//}
