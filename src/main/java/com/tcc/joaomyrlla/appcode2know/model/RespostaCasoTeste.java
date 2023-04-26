package com.tcc.joaomyrlla.appcode2know.model;

import com.tcc.joaomyrlla.appcode2know.dto.RespostaDeCasoTesteDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Table(name = "RESPOSTA_CASO_TESTE")
@Data
@Entity
public class RespostaCasoTeste {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String entrada;

    @Column(nullable = false)
    private String saida;

    @Column(nullable = false)
    private int caso;

    private String status;

    private double tempo;

    @ManyToOne
    @JoinColumn(name = "SUBMISSAO_ID")
    private Submissao submissao = new Submissao();

    public static RespostaCasoTeste toRespostaCasoDeTeste(RespostaDeCasoTesteDTO respostaDeCasoTesteDTO) {
        RespostaCasoTeste respostaCasoTeste = new RespostaCasoTeste();
        BeanUtils.copyProperties(respostaDeCasoTesteDTO, respostaCasoTeste);
        respostaCasoTeste.getSubmissao().setId(respostaDeCasoTesteDTO.getSubmissaoId());

        return respostaCasoTeste;
    }
}
