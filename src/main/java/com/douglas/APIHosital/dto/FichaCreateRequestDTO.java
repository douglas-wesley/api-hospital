package com.douglas.APIHosital.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(name = "FichaCreateRequestDTO", description = "DTO para requisição de criação de ficha médica")
public class FichaCreateRequestDTO {
    @NotBlank(message = "O campo CPF do paciente não pode estar vazio")
    @Schema(description = "Cpf do Paciente" , example = "123.456.789-00")
    private String cpfPaciente;

    @Schema(description = "Nome do Paciente" , example = "João da Silva")
    private String nomePaciente;

    @Schema(description = "Data de Nascimento do Paciente" , example = "1990-05-20")
    private LocalDate dataNascimento;

    @NotBlank(message = "O campo queixa principal não pode estar vazio")
    @Schema(description = "Queixa Principal do Paciente" , example = "Dor de cabeça intensa há 3 dias")
    private String queixaPrincipal;
}
