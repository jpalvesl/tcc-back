package com.tcc.joaomyrlla.appcode2know.model;

import jakarta.persistence.*;
import lombok.Data;

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
    private Usuario criador;
}
