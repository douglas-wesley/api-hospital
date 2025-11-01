package com.douglas.APIHosital.entities;

import com.douglas.APIHosital.enums.Conduta;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="tb_atendimento_medico")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtendimentoMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String parecerMedico;
    private Conduta conduta;
    private String prescricao;

    @OneToOne
    @JoinColumn(name = "ficha_atendimento_id")
    @JsonBackReference
    private FichaAtendimento fichaAtendimento;

}
