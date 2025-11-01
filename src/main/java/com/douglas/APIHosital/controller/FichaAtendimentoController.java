package com.douglas.APIHosital.controller;

import com.douglas.APIHosital.dto.AtendimentoMedicoRequestDTO;
import com.douglas.APIHosital.dto.FichaCreateRequestDTO;
import com.douglas.APIHosital.dto.FichaResponseDTO;
import com.douglas.APIHosital.dto.TriagemRequestDTO;
import com.douglas.APIHosital.enums.StatusAtendimento;
import com.douglas.APIHosital.service.FichaAtendimentoService;
import com.douglas.APIHosital.service.interfaces.IFichaAtendimentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fichas")
public class FichaAtendimentoController {

    @Autowired
    private IFichaAtendimentoService fichaService;

    // Criando uma nova ficha de atendimento
    @PostMapping
    public ResponseEntity<FichaResponseDTO> criarFicha(@RequestBody @Valid FichaCreateRequestDTO dto) {
        FichaResponseDTO fichaCriada = fichaService.criarFicha(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(fichaCriada);
    }

    // Classificando o risco do paciente
    @PutMapping("/{fichaId}/classificar")
    public ResponseEntity<FichaResponseDTO> classificarRisco(@PathVariable Long fichaId, @RequestBody @Valid TriagemRequestDTO dto) {
        FichaResponseDTO fichaClassificada = fichaService.classificarRisco(fichaId,dto);
        return ResponseEntity.ok(fichaClassificada);
    }

    // Atendendo o paciente
    @PostMapping("/{fichaId}/atender")
    public ResponseEntity<FichaResponseDTO> atenderPaciente(@PathVariable Long fichaId, @RequestBody @Valid AtendimentoMedicoRequestDTO dto) {
        FichaResponseDTO fichaAtendida = fichaService.atenderPaciente(fichaId,dto);
        return ResponseEntity.ok(fichaAtendida);
    }

    // Medicando o paciente
    @PostMapping("/{fichaId}/medicar")
    public ResponseEntity<FichaResponseDTO> medicarPaciente(@PathVariable Long fichaId) {
        FichaResponseDTO fichaMedicada = fichaService.medicarPaciente(fichaId);
        return ResponseEntity.ok(fichaMedicada);
    }

    // Concluindo o atendimento
    @PostMapping("/{fichaId}/concluir")
    public ResponseEntity<FichaResponseDTO> concluirAtendimento(@PathVariable Long fichaId) {
        FichaResponseDTO fichaConcluida = fichaService.concluirAtendimento(fichaId);
        return ResponseEntity.ok(fichaConcluida);
    }

    // Buscando a fila por status
    @GetMapping("/fila")
    public ResponseEntity<List<FichaResponseDTO>> buscarFilaPorStatus(@RequestParam("status") StatusAtendimento status) {
        List<FichaResponseDTO> fila = fichaService.buscarFilaPorStatus(status);
        return ResponseEntity.ok(fila);
    }

    // Buscando ficha por ID
    @GetMapping("/{fichaId}")
    public ResponseEntity<FichaResponseDTO> buscarFichaPorId(@PathVariable Long fichaId) {
        FichaResponseDTO ficha = fichaService.buscarFichaPorId(fichaId);
        return ResponseEntity.ok(ficha);
    }
}
