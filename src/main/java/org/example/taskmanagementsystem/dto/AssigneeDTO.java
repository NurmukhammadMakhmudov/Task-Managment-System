package org.example.taskmanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Исполнитель")
public class AssigneeDTO {

    @Schema(description = "Имя Исполнителя")
    private String assigneeName;
    @Schema(description = "Фамилия Исполнителя")
    private String assigneeSurname;
    @Schema(description = "Почта")
    private String assigneeEmail;
    @Schema(description = "Задачи Исполнителя")
    private List<TaskDTO> assigneeTasksList;
}
