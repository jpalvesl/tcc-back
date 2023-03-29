package com.tcc.joaomyrlla.appcode2know.model.multivalorado;

import com.tcc.joaomyrlla.appcode2know.dto.TopicoDTO;
import com.tcc.joaomyrlla.appcode2know.model.Problema;
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

    private String nome;

    @ManyToMany
    @JoinTable(
            name = "TOPICO_PROBLEMA",
            joinColumns = @JoinColumn(name = "TOPICO_ID"),
            inverseJoinColumns = @JoinColumn(name = "PROBLEMA_ID"))
    private List<Problema> problemas =  new ArrayList<>();

    public static Topico toTopico(TopicoDTO topicoDTO) {
        Topico topico = new Topico();
        BeanUtils.copyProperties(topicoDTO, topico);

        return topico;
    }
}
