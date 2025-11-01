package com.douglas.APIHosital.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @NotBlank(message = "O campo email não pode estar vazio")
    @Email(message = "Formato de Email inválido")
    private String email;
    @NotBlank(message = "O campo senha não pode estar vazio")
    private String senha;
}
