package com.douglas.APIHosital.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "LoginResponseDTO", description = "DTO para resposta de login")
public class LoginResponseDTO {
    @Schema(description = "Token JWT")
    private String token;
}
