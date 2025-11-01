package com.douglas.APIHosital.dto;

import com.douglas.APIHosital.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {
    private Long id;
    private String nomeUsuario;
    private String email;
    private Role role;
}
