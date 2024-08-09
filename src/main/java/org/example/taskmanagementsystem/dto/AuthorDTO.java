package org.example.taskmanagementsystem.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorDTO {
    private String authorName;
    private String authorSurname;
    private String authorEmail;
    private List<TaskDTO> authorTaskList;
}
