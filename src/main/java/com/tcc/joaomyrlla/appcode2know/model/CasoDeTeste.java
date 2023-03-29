package com.tcc.joaomyrlla.appcode2know.model;

import com.tcc.joaomyrlla.appcode2know.dto.CasoDeTesteDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Table(name = "CASO_TESTE")
@Data
@Entity
public class CasoDeTeste {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String entrada;

    @Column(nullable = false)
    private String saida;

    @Column(nullable = false)
    private int caso;

    @ManyToOne
    @JoinColumn(name = "PROBLEMA_ID")
    private Problema problema;

    public static CasoDeTeste toCasoDeTeste(CasoDeTesteDTO casoDeTesteDTO) {
        CasoDeTeste casoDeTeste = new CasoDeTeste();
        BeanUtils.copyProperties(casoDeTesteDTO, casoDeTeste);

        return casoDeTeste;
    }
}
