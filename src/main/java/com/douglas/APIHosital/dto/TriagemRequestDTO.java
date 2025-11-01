package com.douglas.APIHosital.dto;

import com.douglas.APIHosital.enums.Risco;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TriagemRequestDTO {
    @NotNull(message = "A classificação de risco é obrigatória.")
    private Risco classificacaoRisco;
}
