package com.tcc.joaomyrlla.appcode2know.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.Lazy;

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
