package com.tcc.joaomyrlla.appcode2know.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.Date;

@Table(name = "TAREFA")
@Data
@Entity
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date dtAbertura;

    @Temporal(TemporalType.DATE)
    private Date dtEncerramento;

    private String descricao;

    private String titulo;

    @ManyToOne
    @JoinColumn(name = "TURMA_ID")
    private Turma turma;

    @ManyToOne
    @JoinColumn(name = "USUARIO_ID")
    private Usuario criador;

    @ManyToMany
    @JoinTable(name = "TAREFA_PROBLEMA",
            joinColumns = @JoinColumn(name = "TAREFA_ID"),
            inverseJoinColumns = @JoinColumn(name = "PROBLEMA_ID"))
    @Lazy
    private ArrayList<Problema> problemas;
}
