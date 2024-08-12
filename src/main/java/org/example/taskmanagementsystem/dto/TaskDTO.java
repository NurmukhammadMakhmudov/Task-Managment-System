package org.example.taskmanagementsystem.dto;


import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Задача")
public class TaskDTO {

    @Schema(description = "Тема Задачи")
    private String title;
    @Schema(description = "Описания задачи")
    private String description;
    @Schema(description = "Статус задачи",example = "inProgress")
    private StatusType status;
    @Schema(description = "Приоретет задачи", example = "HIGH")
    private PriorityType priority;
    @Schema(description = "Почта автора задачи")
    private String author;
    @Schema(description = "Почта исполнителя")
    private String assignee;

}
