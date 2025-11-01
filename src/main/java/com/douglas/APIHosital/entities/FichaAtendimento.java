package com.douglas.APIHosital.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name="tb_ficha_atendimento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FichaAtendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dataHoraChegada;
    private String queixaPrincipal;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    @JsonBackReference
    private Paciente paciente;

    @OneToOne(mappedBy = "fichaAtendimento", cascade = CascadeType.ALL, orphanRemoval = true) // Remove do banco
    @JsonManagedReference
    private AtendimentoMedico atendimentoMedico;

}
