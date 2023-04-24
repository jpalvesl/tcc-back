package com.tcc.joaomyrlla.appcode2know.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.Lazy;

import java.util.Date;
import java.util.List;

@Table(name = "SUBMISSAO")
@Data
@Entity
public class Submissao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String codigoResposta;
    
    private String status = "ok";

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date data = new Date();

    private double tempoMedio = 0;

//    @Column(nullable = false)
    private String linguagem = "Python";;

    @ManyToOne
    @JoinColumn(name = "USUARIO_ID")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "PROBLEMA_ID")
    private Problema problema;

    @OneToMany(mappedBy = "submissao")
    @Lazy
    private List<RespostaCasoTeste> respostasCasoTeste;
}
