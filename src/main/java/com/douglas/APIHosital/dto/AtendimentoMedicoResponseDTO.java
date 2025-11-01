package com.douglas.APIHosital.dto;

import com.douglas.APIHosital.enums.Conduta;
import lombok.Data;

@Data
public class AtendimentoMedicoResponseDTO {
    private Long id;
    private String parecerMedico;
    private Conduta conduta;
    private String prescricao;
}
