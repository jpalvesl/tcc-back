package com.tcc.joaomyrlla.appcode2know.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Table(name = "PROBLEMA")
@Data
@Entity
public class Problema {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;
    
    private int dificuldade;
    
    private String fonte;
    
    private String descricao;
    
    private String textoEntrada;
    
    private String textoSaida;

    @ManyToOne
    @JoinColumn(name = "CRIADOR_ID")
    @Lazy
    private Usuario criador;

    @ManyToMany(mappedBy = "problemas")
    @Lazy
    private List<Tarefa> tarefas;

    @OneToMany(mappedBy = "problema")
    private List<CasoDeTeste> casosDeTeste;
}
