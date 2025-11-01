package com.douglas.APIHosital.dto;

import com.douglas.APIHosital.enums.Conduta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class AtendimentoMedicoRequestDTO {

    @NotBlank(message = "O parecer médico não pode estar vazio.")
    private String parecerMedico;
    @NotNull(message = "A conduta médica é obrigatória.")
    private Conduta conduta;

    private String prescricao;
}
