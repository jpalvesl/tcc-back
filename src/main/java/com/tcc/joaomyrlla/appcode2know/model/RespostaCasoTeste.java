package com.tcc.joaomyrlla.appcode2know.model;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "RESPOSTA_CASO_TESTE")
@Data
@Entity
public class RespostaCasoTeste {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String entrada;

    @Column(nullable = false)
    private String saida;

    @Column(nullable = false)
    private int caso;

    private String status;

    private double tempo;

    @ManyToOne
    @JoinColumn(name = "SUBMISSAO_ID")
    private Submissao submissao;
}
