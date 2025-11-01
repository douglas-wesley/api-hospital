package com.douglas.APIHosital.service.interfaces;

import com.douglas.APIHosital.dto.LoginRequestDTO;
import com.douglas.APIHosital.dto.LoginResponseDTO;
import com.douglas.APIHosital.dto.UsuarioRegisterDTO;
import com.douglas.APIHosital.dto.UsuarioResponseDTO;

public interface IAutenticacaoService {
    UsuarioResponseDTO register(UsuarioRegisterDTO usuarioRegisterDTO);
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
}
