package org.example.taskmanagementsystem.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Воврат Токена после авторизации")
public class AuthResponse {
    @Schema(description = "Токен")
    private String token;
}
