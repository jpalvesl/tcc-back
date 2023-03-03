package com.tcc.joaomyrlla.appcode2know.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Table(name = "INSTITUICAO")
@Data
@Entity
public class Instituicao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    private String campus;

    @OneToMany(mappedBy = "instituicaoAtual")
    @Lazy
    private List<Usuario> alunos;
}
