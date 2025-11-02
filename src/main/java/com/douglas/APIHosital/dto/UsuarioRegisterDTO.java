package com.douglas.APIHosital.dto;


import com.douglas.APIHosital.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
@Schema(name = "UsuarioRegisterDTO", description = "DTO para registro de usuário")
public class UsuarioRegisterDTO {
    @NotBlank(message = "O nome não pode estar vazio")
    @Schema(description = "Nome do usuário", example = "Samuel Silva")
    private String nomeUsuario;

    @NotBlank(message = "O campo email não pode estar vazio")
    @Email(message = "Formato de Email inválido")
    @Schema(description = "Email do usuário", example = "samuel@gmail.com")
    private String email;

    @NotBlank(message = "O campo senha não pode estar vazio")
    @Size( min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    @Schema(description = "Senha do usuário", example = "SenhaForte123!")
    private String senha;

    @NotNull(message = "O campo role não pode estar vazio")
    @Schema(description = "Papel do usuário no sistema", example = "ADMIN")
    private Role role;
}
