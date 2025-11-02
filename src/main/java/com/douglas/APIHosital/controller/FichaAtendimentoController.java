package com.douglas.APIHosital.controller;

import com.douglas.APIHosital.dto.AtendimentoMedicoRequestDTO;
import com.douglas.APIHosital.dto.FichaCreateRequestDTO;
import com.douglas.APIHosital.dto.FichaResponseDTO;
import com.douglas.APIHosital.dto.TriagemRequestDTO;
import com.douglas.APIHosital.enums.StatusAtendimento;
import com.douglas.APIHosital.service.FichaAtendimentoService;
import com.douglas.APIHosital.service.interfaces.IFichaAtendimentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fichas")
@Tag(name = "Ficha de Atendimento", description = "Endpoints para gerenciamento de fichas de atendimento")
public class FichaAtendimentoController {

    @Autowired
    private IFichaAtendimentoService fichaService;

    // Criando uma nova ficha de atendimento
    @PostMapping
    @Operation(summary = "Criar nova ficha de atendimento", description = "Cria uma nova ficha de atendimento para um paciente")
    @ApiResponse(responseCode = "201", description = "Ficha criada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public ResponseEntity<FichaResponseDTO> criarFicha(@RequestBody @Valid FichaCreateRequestDTO dto) {
        FichaResponseDTO fichaCriada = fichaService.criarFicha(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(fichaCriada);
    }

    // Classificando o risco do paciente
    @PutMapping("/{fichaId}/classificar")
    @Operation(summary = "Classificar risco do paciente", description = "Classifica o risco de um paciente em uma ficha de atendimento com base na triagem")
    @ApiResponse(responseCode = "200", description = "Risco classificado com sucesso")
    @ApiResponse(responseCode = "404", description = "Ficha não encontrada")
    public ResponseEntity<FichaResponseDTO> classificarRisco(@PathVariable Long fichaId, @RequestBody @Valid TriagemRequestDTO dto) {
        FichaResponseDTO fichaClassificada = fichaService.classificarRisco(fichaId,dto);
        return ResponseEntity.ok(fichaClassificada);
    }

    // Atendendo o paciente
    @PostMapping("/{fichaId}/atender")
    @Operation(summary = "Atender paciente" , description = "Registra o atendimento médico de um paciente em uma ficha de atendimento")
    @ApiResponse(responseCode = "200", description = "Paciente atendido com sucesso")
    public ResponseEntity<FichaResponseDTO> atenderPaciente(@PathVariable Long fichaId, @RequestBody @Valid AtendimentoMedicoRequestDTO dto) {
        FichaResponseDTO fichaAtendida = fichaService.atenderPaciente(fichaId,dto);
        return ResponseEntity.ok(fichaAtendida);
    }

    // Medicando o paciente
    @PostMapping("/{fichaId}/medicar")
    @Operation(summary = "Medicar paciente", description = "Registra a medicação administrada a um paciente em uma ficha de atendimento")
    @ApiResponse(responseCode = "200", description = "Medicação registrada com sucesso")
    @ApiResponse(responseCode = "404", description = "Ficha não encontrada")
    public ResponseEntity<FichaResponseDTO> medicarPaciente(@PathVariable Long fichaId) {
        FichaResponseDTO fichaMedicada = fichaService.medicarPaciente(fichaId);
        return ResponseEntity.ok(fichaMedicada);
    }

    // Concluindo o atendimento
    @PostMapping("/{fichaId}/concluir")
    @Operation(summary = "Concluir atendimento", description = "Marca o atendimento de uma ficha como concluído")
    @ApiResponse(responseCode = "200", description = "Atendimento concluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Ficha não encontrada")
    public ResponseEntity<FichaResponseDTO> concluirAtendimento(@PathVariable Long fichaId) {
        FichaResponseDTO fichaConcluida = fichaService.concluirAtendimento(fichaId);
        return ResponseEntity.ok(fichaConcluida);
    }

    // Buscando a fila por status
    @GetMapping("/fila")
    @Operation(summary = "Buscar fila por status", description = "Retorna a lista de fichas de atendimento filtradas pelo status")
    @ApiResponse(responseCode = "200", description = "Fila retornada com sucesso")
    @ApiResponse(responseCode = "400", description = "Status inválido")
    public ResponseEntity<List<FichaResponseDTO>> buscarFilaPorStatus(@RequestParam("status") StatusAtendimento status) {
        List<FichaResponseDTO> fila = fichaService.buscarFilaPorStatus(status);
        return ResponseEntity.ok(fila);
    }

    // Buscando ficha por ID
    @GetMapping("/{fichaId}")
    @Operation(summary = "Buscar ficha por ID", description = "Retorna os detalhes de uma ficha de atendimento pelo seu ID")
    @ApiResponse(responseCode = "200", description = "Ficha encontrada com sucesso")
    @ApiResponse(responseCode = "404", description = "Ficha não encontrada")
    public ResponseEntity<FichaResponseDTO> buscarFichaPorId(@PathVariable Long fichaId) {
        FichaResponseDTO ficha = fichaService.buscarFichaPorId(fichaId);
        return ResponseEntity.ok(ficha);
    }
}
