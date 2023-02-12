package com.tcc.joaomyrlla.appcode2know.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;

@Table(name = "SUBMISSAO")
@Data
@Entity
public class Submissao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int caso;
    
    private String entrada;
    
    private String saida;
    
    private String status;

    private Long tempoExecucao;

    @ManyToOne
    @JoinColumn(name = "USUARIO_ID")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "PROBLEMA_ID")
    private Problema problema;

    @ManyToMany
    @JoinTable(name = "SUBMISSAO_CASOS_TESTE",
            joinColumns = @JoinColumn(name = "SUBMISSAO_ID"),
            inverseJoinColumns = @JoinColumn(name = "CASO_TESTE_ID"))
    @Lazy
    private ArrayList<CasoDeTeste> casosDeTeste;
}
