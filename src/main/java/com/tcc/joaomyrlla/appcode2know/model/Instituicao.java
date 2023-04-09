package com.tcc.joaomyrlla.appcode2know.model;

import jakarta.persistence.*;
import lombok.Data;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;

import com.tcc.joaomyrlla.appcode2know.dto.InstituicaoDTO;

import java.util.List;

@Table(name = "INSTITUICAO", uniqueConstraints = { @UniqueConstraint(columnNames = { "nome", "campus" }) })
@Data
@Entity
public class Instituicao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, length = 100)
    private String campus;

    @OneToMany(mappedBy = "instituicaoAtual")
    @Lazy
    private List<Usuario> alunos;

    public static Instituicao toInstituicao(InstituicaoDTO instituicaoDTO) {
        Instituicao instituicao = new Instituicao();
        BeanUtils.copyProperties(instituicaoDTO, instituicao);

        return instituicao;
    }
}
