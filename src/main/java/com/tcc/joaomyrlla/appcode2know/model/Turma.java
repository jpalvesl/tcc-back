package com.tcc.joaomyrlla.appcode2know.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.WhereJoinTable;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.Date;

@Table(name = "TURMA")
@Data
@Entity
public class Turma {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date dtAbertura;

    @Temporal(TemporalType.DATE)
    private Date dtEncerramento;

    private String titulo;

    private String nomeTurma;

    private String Semestre;

    @ManyToOne
    @JoinColumn(name = "INSTITUICAO_ID")
    private Instituicao instituicao;

    @ManyToMany
    @Lazy
//    @WhereJoinTable(clause =  "EH_PROFESSOR = true ")
    private ArrayList<Usuario> professores;

    @ManyToMany
    @JoinTable(name = "MONITOR_TURMA",
            joinColumns = @JoinColumn(name = "TURMA_ID"),
            inverseJoinColumns = @JoinColumn(name = "MONITOR_ID"))
    @Lazy
    private ArrayList<Usuario> monitores;

    @ManyToMany
    @JoinTable(name = "ALUNO_TURMA",
            joinColumns = @JoinColumn(name = "TURMA_ID"),
            inverseJoinColumns = @JoinColumn(name = "ALUNO_ID"))
    @Lazy
    private ArrayList<Usuario> alunos;
}
