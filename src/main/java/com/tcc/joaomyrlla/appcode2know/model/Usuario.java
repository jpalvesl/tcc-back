package com.tcc.joaomyrlla.appcode2know.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;

@Table(name = "USUARIO")
@Data
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    private String email;

    private String usuario;

    private String senha;

    private boolean ehAdm;

    private boolean ehProfessor;

   @ManyToOne
   @JoinColumn(name = "INSTITUICAO_ATUAL_ID")
   private Instituicao instituicaoAtual;

    @ManyToMany
    @JoinTable(name = "PROFESSOR_TURMA",
            joinColumns = @JoinColumn(name = "PROFESSOR_ID"),
            inverseJoinColumns = @JoinColumn(name = "TURMA_ID"))
    @Lazy
    private List<Turma> turmasProfessor;

    @ManyToMany(mappedBy = "alunos")
    @Lazy
    private List<Turma> turmasAluno;
}