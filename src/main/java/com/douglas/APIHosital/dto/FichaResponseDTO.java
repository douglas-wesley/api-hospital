package com.douglas.APIHosital.dto;

import com.douglas.APIHosital.enums.Risco;
import com.douglas.APIHosital.enums.StatusAtendimento;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FichaResponseDTO {
    private Long id;
    private LocalDateTime dataHoraChegada;
    private String queixaPrincipal;
    private Risco classificacaoRisco;
    private StatusAtendimento statusAtendimento;

    private PacienteResponseDTO paciente;
    private AtendimentoMedicoResponseDTO atendimentoMedico;

}
