package com.douglas.APIHosital.dto;

import com.douglas.APIHosital.enums.Conduta;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Schema(name = "AtendimentoMedicoRequestDTO", description = "DTO para requisição de atendimento médico")
public class AtendimentoMedicoRequestDTO {

    @NotBlank(message = "O parecer médico não pode estar vazio.")
    @Schema(description = "Parecer médico sobre o atendimento ao paciente", example = "Paciente apresenta sintomas leves e deve ser monitorado.")
    private String parecerMedico;

    @NotNull(message = "A conduta médica é obrigatória.")
    @Schema(description = "Conduta médica adotada no atendimento", example = "ALTA")
    private Conduta conduta;

    @Schema(description = "Prescrição médica para o paciente", example = "Tomar 1 comprimido de analgésico a cada 8 horas.")
    private String prescricao;
}
