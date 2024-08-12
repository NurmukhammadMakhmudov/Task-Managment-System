package org.example.taskmanagementsystem.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Запрос на авторизацию")
public class AuthRequest {
    @Schema(description = "Почта", example = "example@example.com")
    private String username;
    @Schema(description = "Пароль")
    private String password;
}
