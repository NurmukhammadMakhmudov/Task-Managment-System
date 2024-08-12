package org.example.taskmanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.taskmanagementsystem.Enums.RolesType;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Регистрационая информация")
public class RegisterRequest {
    @Schema(description = "Имя")
    private String firstName;
    @Schema(description = "Фамилиля")
    private String lastName;
    @Schema(description = "Почта", example = "example@example.com")
    private String email;
    @Schema(description = "Пароль")
    private String password;
    @Nullable
    @Schema(description = "Роль", example = "AUTHOR")
    private RolesType role;


    public RegisterRequest() {
        this.role = RolesType.ASSIGNEE;
    }
}
