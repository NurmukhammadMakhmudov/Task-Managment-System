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
@Schema(description = "Автор")
public class AuthorDTO {
    @Schema(description = "Имя автора")
    private String authorName;
    @Schema(description = "Фамилия")
    private String authorSurname;
    @Schema(description = "Почта")
    private String authorEmail;
    @Schema(description = "Задачи поставленые этим автором")
    private List<TaskDTO> authorTaskList;
}
