package org.example.taskmanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Регистрационая информация")
public class CommentDTO {
    @Schema(description = "Комментарий")
    private String comment;
    @Schema(description = "Автор")
    private String author;
    @Schema(description = "Дата создания комментария")
    private LocalDateTime date;

}
