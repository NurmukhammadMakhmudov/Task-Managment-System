package org.example.taskmanagementsystem.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.taskmanagementsystem.Enums.PriorityType;
import org.example.taskmanagementsystem.Enums.StatusType;
import org.example.taskmanagementsystem.model.Comments;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDTO {

    private long id;
    private String title;
    private String description;
    private StatusType status;
    private PriorityType priority;
    private String author;
    private String assignee;

}
