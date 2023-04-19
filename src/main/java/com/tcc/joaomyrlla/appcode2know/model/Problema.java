package com.tcc.joaomyrlla.appcode2know.model;

import com.tcc.joaomyrlla.appcode2know.dto.ProblemaDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;

@Table(name = "PROBLEMA")
@Data
@Entity
public class Problema {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private int dificuldade;
    
    private String fonte;

    @Column(length = 2000)
    private String descricao;
    
    @Column(length = 1000)
    private String textoEntrada;

    @Column(length = 1000)
    private String textoSaida;

    @Column(nullable = false)
    private boolean problemaDeProva;

    @Column(nullable = false)
    private double tempoLimite;

    @Column(nullable = false)
    private Integer limiteDeMemoria;

    @ManyToOne
    @JoinColumn(name = "CRIADOR_ID")
    @Lazy
    private Usuario criador;

    @ManyToMany(mappedBy = "problemas")
    @Lazy
    private List<Tarefa> tarefas = new ArrayList<>();

    @OneToMany(mappedBy = "problema")
    private List<CasoDeTeste> casosDeTeste = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "TOPICO_PROBLEMA",
            joinColumns = @JoinColumn(name = "PROBLEMA_ID"),
            inverseJoinColumns = @JoinColumn(name = "TOPICO_ID"))
    private List<Topico> topicos = new ArrayList<>();

    public static Problema toProblema(ProblemaDTO problemaDTO) {
        Problema problema = new Problema();
        BeanUtils.copyProperties(problemaDTO, problema);

        problemaDTO.getTopicos().forEach(mapTopico -> {
            Topico topico = new Topico();
            Integer topicoId = (Integer) mapTopico.get("id");
            String topicoNome = (String) mapTopico.get("nome");

            topico.setId(topicoId.longValue());
            topico.setNome(topicoNome);
            problema.getTopicos().add(topico);
        });

        return problema;
    }
}
