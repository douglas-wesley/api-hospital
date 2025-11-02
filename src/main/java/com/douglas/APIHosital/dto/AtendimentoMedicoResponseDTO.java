package com.douglas.APIHosital.dto;

import com.douglas.APIHosital.enums.Conduta;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AtendimentoMedicoResponseDTO", description = "DTO para resposta de atendimento médico")
public class AtendimentoMedicoResponseDTO {
    @Schema(description = "ID do atendimento médico", example = "1")
    private Long id;

    @Schema(description = "Parecer médico sobre o atendimento ao paciente", example = "Paciente apresenta sintomas leves e deve ser monitorado.")
    private String parecerMedico;

    @Schema(description = "Conduta médica adotada no atendimento", example = "ALTA")
    private Conduta conduta;

    @Schema(description = "Prescrição médica para o paciente", example = "Tomar 1 comprimido de analgésico a cada 8 horas.")
    private String prescricao;
}
