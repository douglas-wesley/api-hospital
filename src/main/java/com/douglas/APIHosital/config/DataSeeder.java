package com.douglas.APIHosital.config;

import com.douglas.APIHosital.entities.Usuario;
import com.douglas.APIHosital.enums.Role;
import com.douglas.APIHosital.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

@Configuration
public class DataSeeder implements CommandLineRunner {

    // Logger para exibir mensagens no console
    private static final Logger LOGGER = Logger.getLogger(DataSeeder.class.getName());

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // Injeção de dependências via construtor
    public DataSeeder(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        LOGGER.info("Verificando e populando usuários padrão (seeder)...");

        // Usuários padrão a serem criados
        seedUserIfNotFound("Ana Atendente", "atendente@hospital.com", "password123", Role.ATENDENTE);
        seedUserIfNotFound("Enrique Enfermeiro", "enfermeiro@hospital.com", "password123", Role.ENFERMEIRO);
        seedUserIfNotFound("Dra. Maria Medica", "medico@hospital.com", "password123", Role.MEDICO);
        seedUserIfNotFound("Felipe Farmacia", "farmacia@hospital.com", "password123", Role.FARMACIA);

        LOGGER.info("Seeder finalizado.");
    }

    // Cria um usuário se ele não existir
    private void seedUserIfNotFound(String nome, String email, String senha, Role role) {

        if (usuarioRepository.findFirstByEmail(email).isEmpty()) {

            Usuario novoUsuario = new Usuario();
            novoUsuario.setNomeUsuario(nome);
            novoUsuario.setEmail(email);

            novoUsuario.setSenha(passwordEncoder.encode(senha));
            novoUsuario.setRole(role);

            usuarioRepository.save(novoUsuario);
            LOGGER.info("Usuário " + email + " criado com sucesso.");
        }
    }
}