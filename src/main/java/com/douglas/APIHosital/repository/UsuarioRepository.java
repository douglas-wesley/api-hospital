package com.douglas.APIHosital.repository;

import com.douglas.APIHosital.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
}
