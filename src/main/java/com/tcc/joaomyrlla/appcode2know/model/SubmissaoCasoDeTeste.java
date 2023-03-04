package com.tcc.joaomyrlla.appcode2know.model;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "SUBMISSAO_CASOS_TESTE")
@Entity
@Data
public class SubmissaoCasoDeTeste {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "submissao_id")
    private Submissao submissao;

    @ManyToOne
    @JoinColumn(name = "caso_teste_id")
    private CasoDeTeste casoDeTeste;

    private String estado;
}
