package com.douglas.APIHosital.entities;

import com.douglas.APIHosital.enums.Risco;
import com.douglas.APIHosital.enums.StatusAtendimento;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<FichaAtendimento> fichaAtendimentos;

    @Enumerated(EnumType.STRING)
    private Risco risco;

    @Enumerated(EnumType.STRING)
    private StatusAtendimento statusAtendimento;
}
