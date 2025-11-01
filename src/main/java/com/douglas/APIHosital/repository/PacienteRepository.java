package com.douglas.APIHosital.repository;

import com.douglas.APIHosital.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente,Long> {
}
