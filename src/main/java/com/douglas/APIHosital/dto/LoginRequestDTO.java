package com.douglas.APIHosital.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(name = "LoginRequestDTO", description = "DTO para requisição de login")
public class LoginRequestDTO {
    @NotBlank(message = "O campo email não pode estar vazio")
    @Email(message = "Formato de Email inválido")
    @Schema(description = "Email do usuário", example = "emaildousuario@gmail.com")
    private String email;

    @NotBlank(message = "O campo senha não pode estar vazio")
    @Schema(description = "Senha do usuário", example = "SenhaSegura123!")
    private String senha;
}
