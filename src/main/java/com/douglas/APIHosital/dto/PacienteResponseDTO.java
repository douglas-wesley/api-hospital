package com.douglas.APIHosital.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "PacienteResponseDTO", description = "DTO para resposta de paciente")
public class PacienteResponseDTO {
    @Schema(description = "ID do paciente", example = "1")
    private Long id;
    @Schema(description = "Nome completo do paciente", example = "João da Silva")
    private String nomePaciente;
    @Schema(description = "CPF do paciente", example = "123.456.789-00")
    private String cpf;
}
