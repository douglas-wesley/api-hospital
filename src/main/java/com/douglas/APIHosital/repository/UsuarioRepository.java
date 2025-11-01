package com.douglas.APIHosital.repository;

import com.douglas.APIHosital.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    UserDetails findByEmail(String email);
    Optional<Usuario> findFirstByEmail(String email);
}
