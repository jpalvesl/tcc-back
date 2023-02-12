package com.tcc.joaomyrlla.appcode2know.model;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "CASO_TESTE")
@Data
@Entity
public class CasoDeTeste {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    private String entrada;
    
    private String saida;
    
    private int caso;

    @ManyToOne
    @JoinColumn(name = "PROBLEMA_ID")
    private Problema problema;
}
