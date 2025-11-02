package com.douglas.APIHosital.controller;


import com.douglas.APIHosital.dto.LoginRequestDTO;
import com.douglas.APIHosital.dto.LoginResponseDTO;
import com.douglas.APIHosital.dto.UsuarioRegisterDTO;
import com.douglas.APIHosital.dto.UsuarioResponseDTO;
import com.douglas.APIHosital.service.interfaces.IAutenticacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints para autenticação de usuários")
public class AutenticacaoController {

    private IAutenticacaoService autenticacaoService;


    public AutenticacaoController(IAutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    @Operation(summary = "Login de usuário", description = "Autentica um usuário e retorna um token JWT")
    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Credenciais inválidas")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        LoginResponseDTO response = autenticacaoService.login(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar novo usuário", description = "Cria um novo usuário no sistema")
    @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public ResponseEntity<UsuarioResponseDTO> register(@RequestBody @Valid UsuarioRegisterDTO dto) {
        UsuarioResponseDTO response = autenticacaoService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
