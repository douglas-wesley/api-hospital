package com.douglas.APIHosital.dto;


import com.douglas.APIHosital.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class UsuarioRegisterDTO {
    @NotBlank(message = "O nome não pode estar vazio")
    private String nomeUsuario;

    @NotBlank(message = "O campo email não pode estar vazio")
    @Email(message = "Formato de Email inválido")
    private String email;

    @NotBlank(message = "O campo senha não pode estar vazio")
    @Size( min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String senha;

    @NotNull(message = "O campo role não pode estar vazio")
    private Role role;
}
