package com.tcc.joaomyrlla.appcode2know.model;

import com.tcc.joaomyrlla.appcode2know.dto.TurmaDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.WhereJoinTable;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "TURMA")
@Data
@Entity
public class Turma {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dtAbertura;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dtEncerramento;

    @Column(nullable = false)
    private String nomeTurma;

    @Column(nullable = false)
    private String semestre;

    @ManyToOne
    @JoinColumn(name = "INSTITUICAO_ID")
    private Instituicao instituicao;

    @ManyToMany
    @JoinTable(name = "PROFESSOR_TURMA",
            joinColumns = @JoinColumn(name = "TURMA_ID"),
            inverseJoinColumns = @JoinColumn(name = "PROFESSOR_ID"))
    @Lazy
    private List<Usuario> professores = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "MONITOR_TURMA",
            joinColumns = @JoinColumn(name = "TURMA_ID"),
            inverseJoinColumns = @JoinColumn(name = "MONITOR_ID"))
    @Lazy
    private List<Usuario> monitores = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "ALUNO_TURMA",
            joinColumns = @JoinColumn(name = "TURMA_ID"),
            inverseJoinColumns = @JoinColumn(name = "ALUNO_ID"))
    @Lazy
    private List<Usuario> alunos = new ArrayList<>();

    @OneToMany
    private List<Tarefa> tarefas = new ArrayList<>();

    public static Turma toTurma(TurmaDTO turmaDTO) {
        Turma turma = new Turma();
        BeanUtils.copyProperties(turmaDTO, turma);



        return turma;
    }
}
