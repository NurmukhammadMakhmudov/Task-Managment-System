package org.example.taskmanagementsystem.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.example.taskmanagementsystem.Enums.PriorityType;
import org.example.taskmanagementsystem.Enums.StatusType;

import java.util.List;

@Entity
@Table(name = "task")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusType status;

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    private PriorityType priority;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonBackReference("user-authored")
    private Users author;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    @JsonBackReference("user-assigned")
    private Users assignee;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    @JsonBackReference("task")
    private List<Comments> comments;
}
