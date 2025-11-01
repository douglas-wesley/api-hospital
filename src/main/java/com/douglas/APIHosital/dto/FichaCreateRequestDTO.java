package com.douglas.APIHosital.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FichaCreateRequestDTO {
    @NotBlank(message = "O campo CPF do paciente não pode estar vazio")
    private String cpfPaciente;

    private String nomePaciente;
    private LocalDate dataNascimento;

    @NotBlank(message = "O campo queixa principal não pode estar vazio")
    private String queixaPrincipal;
}
