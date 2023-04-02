package com.tcc.joaomyrlla.appcode2know.model;

import com.tcc.joaomyrlla.appcode2know.dto.TarefaDTO;
import com.tcc.joaomyrlla.appcode2know.utils.DateUtils;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "TAREFA")
@Data
@Entity
public class Tarefa {
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
    private String descricao;

    @Column(nullable = false)
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
    private List<Problema> problemas = new ArrayList<>();

    public static Tarefa toTarefa(TarefaDTO tarefaDTO) {
        Tarefa tarefa = new Tarefa();
        BeanUtils.copyProperties(tarefaDTO, tarefa);
        tarefa.setDtAbertura(DateUtils.toDate("yyyy-MM-dd", tarefaDTO.getDtAbertura()));
        tarefa.setDtEncerramento(DateUtils.toDate("yyyy-MM-dd", tarefaDTO.getDtAbertura()));

        return tarefa;
    }
}
