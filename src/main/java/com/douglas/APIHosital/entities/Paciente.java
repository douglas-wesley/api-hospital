package com.douglas.APIHosital.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tb_paciente")
@Getter
@Setter
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomePaciente;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;

    @OneToMany(mappedBy = "paciente")
    @JsonIgnore
    private List<FichaAtendimento> fichaAtendimentos;

}
