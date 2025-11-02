package com.douglas.APIHosital.service;


import com.douglas.APIHosital.dto.LoginRequestDTO;
import com.douglas.APIHosital.dto.LoginResponseDTO;
import com.douglas.APIHosital.dto.UsuarioRegisterDTO;
import com.douglas.APIHosital.dto.UsuarioResponseDTO;
import com.douglas.APIHosital.entities.Usuario;
import com.douglas.APIHosital.repository.UsuarioRepository;
import com.douglas.APIHosital.security.TokenService;
import com.douglas.APIHosital.service.exceptions.EmailCadastradoException;
import com.douglas.APIHosital.service.interfaces.IAutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements IAutenticacaoService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Override
    public UsuarioResponseDTO register(UsuarioRegisterDTO dto) {

        if (usuarioRepository.findFirstByEmail(dto.getEmail()).isPresent()) {
            throw new EmailCadastradoException("Email já está em uso.");
        }

        // Criptografia da Senha
        String senhaCriptografada = passwordEncoder.encode(dto.getSenha());

        // Criação de um novo usuário
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNomeUsuario(dto.getNomeUsuario());
        novoUsuario.setEmail(dto.getEmail());
        novoUsuario.setSenha(senhaCriptografada);
        novoUsuario.setRole(dto.getRole());

        // Salva o novo usuário no banco de dados
        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        // Retorna os dados do usuário salvo
        return new UsuarioResponseDTO(
                usuarioSalvo.getId(),
                usuarioSalvo.getNomeUsuario(),
                usuarioSalvo.getEmail(),
                usuarioSalvo.getRole()
        );
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO dto) {
        // Realiza a autenticação do usuário
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha());
        // Se a autenticação for bem-sucedida, gera o token
        var auth = this.authenticationManager.authenticate(usernamePassword);
        // Gera o token JWT
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
        // Retorna o token no formato esperado
        return new LoginResponseDTO(token);
    }
}
