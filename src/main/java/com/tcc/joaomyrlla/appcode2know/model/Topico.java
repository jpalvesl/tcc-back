package com.tcc.joaomyrlla.appcode2know.model;

import com.tcc.joaomyrlla.appcode2know.dto.TopicoDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Table(name = "TOPICO")
@Data
@Entity
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @ManyToMany(mappedBy = "topicos")
    private List<Problema> problemas =  new ArrayList<>();

    public static Topico toTopico(TopicoDTO topicoDTO) {
        Topico topico = new Topico();
        BeanUtils.copyProperties(topicoDTO, topico);

        return topico;
    }
}
