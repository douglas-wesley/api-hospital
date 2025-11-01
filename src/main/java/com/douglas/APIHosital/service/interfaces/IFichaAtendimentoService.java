package com.douglas.APIHosital.service.interfaces;


import com.douglas.APIHosital.dto.AtendimentoMedicoRequestDTO;
import com.douglas.APIHosital.dto.FichaCreateRequestDTO;
import com.douglas.APIHosital.dto.FichaResponseDTO;
import com.douglas.APIHosital.dto.TriagemRequestDTO;
import com.douglas.APIHosital.enums.StatusAtendimento;

import java.util.List;

public interface IFichaAtendimentoService {


    FichaResponseDTO criarFicha(FichaCreateRequestDTO dto);
    FichaResponseDTO classificarRisco(Long fichaId, TriagemRequestDTO dto);
    FichaResponseDTO atenderPaciente(Long fichaId, AtendimentoMedicoRequestDTO dto);
    FichaResponseDTO medicarPaciente(Long fichaId);
    FichaResponseDTO concluirAtendimento(Long fichaId);
    List<FichaResponseDTO> buscarFilaPorStatus(StatusAtendimento status);
    FichaResponseDTO buscarFichaPorId(Long fichaId);
}
