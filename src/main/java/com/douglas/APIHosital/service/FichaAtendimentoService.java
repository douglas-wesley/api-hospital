package com.douglas.APIHosital.service;

import com.douglas.APIHosital.dto.AtendimentoMedicoRequestDTO;
import com.douglas.APIHosital.dto.FichaCreateRequestDTO;
import com.douglas.APIHosital.dto.FichaResponseDTO;
import com.douglas.APIHosital.dto.TriagemRequestDTO;
import com.douglas.APIHosital.entities.AtendimentoMedico;
import com.douglas.APIHosital.entities.FichaAtendimento;
import com.douglas.APIHosital.entities.Paciente;
import com.douglas.APIHosital.enums.Conduta;
import com.douglas.APIHosital.enums.StatusAtendimento;
import com.douglas.APIHosital.repository.FichaAtendimentoRepository;
import com.douglas.APIHosital.repository.PacienteRepository;
import com.douglas.APIHosital.service.exceptions.BusinessRuleException;
import com.douglas.APIHosital.service.exceptions.ResourceNotFoundException;
import com.douglas.APIHosital.service.interfaces.IFichaAtendimentoService;
import com.douglas.APIHosital.service.mapper.MapperService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FichaAtendimentoService implements IFichaAtendimentoService {

    @Autowired
    private FichaAtendimentoRepository fichaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MapperService mapper;


    @Override
    @Transactional
    public FichaResponseDTO criarFicha(FichaCreateRequestDTO dto) {
        // 1. Encontrar ou criar o paciente
        Paciente paciente = pacienteRepository.findByCpf(dto.getCpfPaciente())
                .orElseGet(() -> criarNovoPaciente(dto)); // Verificar isso aqui

        // 2. Regra de Negócio: "impedir nova ficha ativa para paciente já em atendimento"
        fichaRepository.findByPacienteIdAndStatusAtendimentoNot(paciente.getId(), StatusAtendimento.CONCLUIDO)
                .ifPresent(fichaAtiva -> {
                    throw new BusinessRuleException("Paciente já possui uma ficha em atendimento ativo.");
                });

        // 3. Criar a nova ficha
        FichaAtendimento novaFicha = new FichaAtendimento();
        novaFicha.setPaciente(paciente);
        novaFicha.setQueixaPrincipal(dto.getQueixaPrincipal());
        novaFicha.setDataHoraChegada(LocalDateTime.now());
        novaFicha.setStatusAtendimento(StatusAtendimento.AGUARDANDO_ATENDIMENTO); // Status inicial

        // 4. Salvar no banco
        FichaAtendimento fichaSalva = fichaRepository.save(novaFicha);

        // 5. Retornar o DTO
        return mapper.toFichaResponseDTO(fichaSalva);
    }

    private Paciente criarNovoPaciente(FichaCreateRequestDTO dto) {
        if (dto.getNomePaciente() == null || dto.getDataNascimento() == null) {
            throw new BusinessRuleException("Nome e Data de Nascimento são obrigatórios para novos pacientes.");
        }
        Paciente novoPaciente = new Paciente();
        novoPaciente.setCpf(dto.getCpfPaciente());
        novoPaciente.setNomePaciente(dto.getNomePaciente());
        novoPaciente.setDataNascimento(dto.getDataNascimento());
        // (Você pode adicionar mais campos se quiser, ex: telefone)
        return pacienteRepository.save(novoPaciente);
    }


    @Override
    @Transactional
    public FichaResponseDTO classificarRisco(Long fichaId, TriagemRequestDTO dto) {
        FichaAtendimento ficha = buscarFichaPeloId(fichaId);

        // Validação de Status
        if (ficha.getStatusAtendimento() != StatusAtendimento.AGUARDANDO_ATENDIMENTO) {
            throw new BusinessRuleException("Ficha não está aguardando triagem.");
        }

        ficha.setClassificacaoRisco(dto.getClassificacaoRisco());
        ficha.setStatusAtendimento(StatusAtendimento.AGUARDANDO_MEDICO); // Próxima etapa

        FichaAtendimento fichaSalva = fichaRepository.save(ficha);
        return mapper.toFichaResponseDTO(fichaSalva);
    }

    @Override
    @Transactional
    public FichaResponseDTO atenderPaciente(Long fichaId, AtendimentoMedicoRequestDTO dto) {
        FichaAtendimento ficha = buscarFichaPeloId(fichaId);

        // Validação de Status
        if (ficha.getStatusAtendimento() != StatusAtendimento.AGUARDANDO_MEDICO) {
            throw new BusinessRuleException("Ficha não está aguardando atendimento médico.");
        }

        // 1. Criar a entidade de atendimento
        AtendimentoMedico atendimento = new AtendimentoMedico();
        atendimento.setParecerMedico(dto.getParecerMedico());
        atendimento.setConduta(dto.getConduta());
        atendimento.setPrescricao(dto.getPrescricao());

        // 2. Sincronizar o relacionamento 1-para-1
        ficha.setAtendimentoMedico(atendimento);
        atendimento.setFichaAtendimento(ficha); // (Assumindo que você tem o setter na entidade)

        // 3. Atualizar o status da ficha com base na conduta
        if (dto.getConduta() == Conduta.MEDICACAO) {
            ficha.setStatusAtendimento(StatusAtendimento.AGUARDANDO_MEDICACAO);
        } else {
            // Se for ALTA ou ENCAMINHAMENTO, concluímos direto
            ficha.setStatusAtendimento(StatusAtendimento.CONCLUIDO);
        }

        FichaAtendimento fichaSalva = fichaRepository.save(ficha); // O Cascade salva o AtendimentoMedico junto
        return mapper.toFichaResponseDTO(fichaSalva);
    }

    @Override
    @Transactional
    public FichaResponseDTO medicarPaciente(Long fichaId) {
        FichaAtendimento ficha = buscarFichaPeloId(fichaId);

        if (ficha.getStatusAtendimento() != StatusAtendimento.AGUARDANDO_MEDICACAO) {
            throw new BusinessRuleException("Ficha não está aguardando medicação.");
        }

        // Simulação do fluxo de medicação
        ficha.setStatusAtendimento(StatusAtendimento.EM_MEDICACAO);
        FichaAtendimento fichaEmMedicacao = fichaRepository.save(ficha);

        // "Ao concluir, o paciente é reenviado ao médico"
        fichaEmMedicacao.setStatusAtendimento(StatusAtendimento.AGUARDANDO_ALTA_MEDICA);
        FichaAtendimento fichaReenviada = fichaRepository.save(fichaEmMedicacao);

        return mapper.toFichaResponseDTO(fichaReenviada);
    }

    @Override
    @Transactional
    public FichaResponseDTO concluirAtendimento(Long fichaId) {
        FichaAtendimento ficha = buscarFichaPeloId(fichaId);

        if (ficha.getStatusAtendimento() != StatusAtendimento.AGUARDANDO_ALTA_MEDICA) {
            throw new BusinessRuleException("Paciente não está aguardando alta médica.");
        }

        ficha.setStatusAtendimento(StatusAtendimento.CONCLUIDO);
        FichaAtendimento fichaSalva = fichaRepository.save(ficha);
        return mapper.toFichaResponseDTO(fichaSalva);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FichaResponseDTO> buscarFilaPorStatus(StatusAtendimento status) {
        List<FichaAtendimento> fichas;

        // Requisito 002: "priorização por risco"
        if (status == StatusAtendimento.AGUARDANDO_MEDICO) {
            fichas = fichaRepository.findByStatusAtendimentoOrderByClassificacaoRiscoAsc(status);
        } else {
            fichas = fichaRepository.findByStatusAtendimento(status);
        }

        // Converte a Lista de Entidades para uma Lista de DTOs
        return fichas.stream()
                .map(mapper::toFichaResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public FichaResponseDTO buscarFichaPorId(Long fichaId) {
        FichaAtendimento ficha = buscarFichaPeloId(fichaId);
        return mapper.toFichaResponseDTO(ficha);
    }

    private FichaAtendimento buscarFichaPeloId(Long fichaId) {
        return fichaRepository.findById(fichaId)
                .orElseThrow(() -> new ResourceNotFoundException("Ficha de Atendimento não encontrada com o ID: " + fichaId));
    }
}
