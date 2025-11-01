package com.douglas.APIHosital.repository;

import com.douglas.APIHosital.entities.FichaAtendimento;
import com.douglas.APIHosital.enums.StatusAtendimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FichaAtendimentoRepository extends JpaRepository<FichaAtendimento, Long> {
    List<FichaAtendimento> findByStatusAtendimento(StatusAtendimento status);
    Optional<FichaAtendimento> findByPacienteIdAndStatusAtendimentoNot(Long pacienteId, StatusAtendimento status);
    List<FichaAtendimento> findByStatusAtendimentoOrderByClassificacaoRiscoAsc(StatusAtendimento status);
}
