package com.tcc.joaomyrlla.appcode2know.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Table(name = "SUBMISSAO")
@Data
@Entity
public class Submissao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private int caso;

    @Column(nullable = false)
    private String codigoResposta;
    
    @Column(nullable = false)
    private String saida; // saida a ser comparada com o problema
    
    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Long tempoExecucao;

    @ManyToOne
    @JoinColumn(name = "USUARIO_ID")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "PROBLEMA_ID")
    private Problema problema;

    @OneToMany(mappedBy = "submissao")
    @Lazy
    private Set<SubmissaoCasoDeTeste> casosDeTeste;
}
