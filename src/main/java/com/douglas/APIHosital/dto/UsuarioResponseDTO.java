package com.douglas.APIHosital.dto;

import com.douglas.APIHosital.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "UsuarioResponseDTO", description = "DTO para resposta de usuário")
public class UsuarioResponseDTO {
    @Schema(description = "ID do usuário", example = "1")
    private Long id;

    @Schema(description = "Nome do usuário", example = "fernando")
    private String nomeUsuario;

    @Schema(description = "Email do usuário", example = "fernando@gmail.com")
    private String email;

    @Schema(description = "Papel do usuário no sistema", example = "MEDICO")
    private Role role;
}
