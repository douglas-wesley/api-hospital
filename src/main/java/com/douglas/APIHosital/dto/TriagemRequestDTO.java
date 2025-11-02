package com.douglas.APIHosital.dto;

import com.douglas.APIHosital.enums.Risco;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(name = "TriagemRequestDTO", description = "DTO para requisição de triagem")
public class TriagemRequestDTO {
    @NotNull(message = "A classificação de risco é obrigatória.")
    @Schema(description = "Classificação de risco do paciente na triagem", example = "VERDE")
    private Risco classificacaoRisco;
}
