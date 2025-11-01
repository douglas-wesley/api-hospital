package com.douglas.APIHosital.service.mapper;


import com.douglas.APIHosital.dto.AtendimentoMedicoResponseDTO;
import com.douglas.APIHosital.dto.FichaResponseDTO;
import com.douglas.APIHosital.dto.PacienteResponseDTO;
import com.douglas.APIHosital.entities.AtendimentoMedico;
import com.douglas.APIHosital.entities.FichaAtendimento;
import com.douglas.APIHosital.entities.Paciente;
import org.springframework.stereotype.Component;

@Component
public class MapperService {

    public FichaResponseDTO toFichaResponseDTO(FichaAtendimento ficha){
        if (ficha == null){
            return null;
        }

        // Mapeia os campos simples
        FichaResponseDTO dto = new FichaResponseDTO();
        dto.setId(ficha.getId());
        dto.setDataHoraChegada(ficha.getDataHoraChegada());
        dto.setQueixaPrincipal(ficha.getQueixaPrincipal());
        dto.setClassificacaoRisco(ficha.getClassificacaoRisco());
        dto.setStatusAtendimento(ficha.getStatusAtendimento());

        // Mapeia os DTOs aninhados
        dto.setPaciente(toPacienteResponseDTO(ficha.getPaciente()));
        dto.setAtendimentoMedico(toAtendimentoMedicoResponseDTO(ficha.getAtendimentoMedico()));

        return dto;
    }

    public PacienteResponseDTO toPacienteResponseDTO(Paciente paciente) {
        if (paciente == null) {
            return null;
        }

        PacienteResponseDTO dto = new PacienteResponseDTO();
        dto.setId(paciente.getId());
        dto.setNomePaciente(paciente.getNomePaciente());
        dto.setCpf(paciente.getCpf());

        return dto;
    }

    public AtendimentoMedicoResponseDTO toAtendimentoMedicoResponseDTO(AtendimentoMedico atendimento) {
        if (atendimento == null) {
            return null; // É normal o atendimento ser nulo se a ficha ainda não foi atendida
        }

        AtendimentoMedicoResponseDTO dto = new AtendimentoMedicoResponseDTO();
        dto.setId(atendimento.getId());
        dto.setParecerMedico(atendimento.getParecerMedico());
        dto.setConduta(atendimento.getConduta());
        dto.setPrescricao(atendimento.getPrescricao());

        return dto;
    }
}
