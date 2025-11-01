package com.douglas.APIHosital.dto;

import lombok.Data;

@Data
public class PacienteResponseDTO {
    private Long id;
    private String nomePaciente;
    private String cpf;
}
