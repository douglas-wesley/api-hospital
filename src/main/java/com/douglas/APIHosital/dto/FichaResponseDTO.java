package com.douglas.APIHosital.dto;

import com.douglas.APIHosital.enums.Risco;
import com.douglas.APIHosital.enums.StatusAtendimento;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(name = "FichaResponseDTO", description = "DTO para resposta de ficha médica")
public class FichaResponseDTO {
    @Schema(description = "ID da ficha médica", example = "1")
    private Long id;
    @Schema(description = "Data e hora de chegada do paciente", example = "2024-06-15T14:30:00")
    private LocalDateTime dataHoraChegada;
    @Schema(description = "Queixa principal do paciente", example = "Dor de cabeça intensa há 3 dias")
    private String queixaPrincipal;
    @Schema(description = "Classificação de risco do paciente", example = "VERMELHO")
    private Risco classificacaoRisco;
    @Schema(description = "Status do atendimento", example = "EM_ATENDIMENTO")
    private StatusAtendimento statusAtendimento;

    @Schema(description = "Dados do paciente associado à ficha médica")
    private PacienteResponseDTO paciente;
    @Schema(description = "Dados do atendimento médico associado à ficha médica")
    private AtendimentoMedicoResponseDTO atendimentoMedico;

}
