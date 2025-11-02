package com.douglas.APIHosital.controller;


import com.douglas.APIHosital.dto.LoginRequestDTO;
import com.douglas.APIHosital.dto.LoginResponseDTO;
import com.douglas.APIHosital.dto.UsuarioRegisterDTO;
import com.douglas.APIHosital.dto.UsuarioResponseDTO;
import com.douglas.APIHosital.service.interfaces.IAutenticacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    private IAutenticacaoService autenticacaoService;

    public AutenticacaoController(IAutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        LoginResponseDTO response = autenticacaoService.login(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDTO> register(@RequestBody @Valid UsuarioRegisterDTO dto) {
        UsuarioResponseDTO response = autenticacaoService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
