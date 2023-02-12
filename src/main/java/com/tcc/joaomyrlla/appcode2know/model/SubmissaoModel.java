package com.tcc.joaomyrlla.appcode2know.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "TB_SUBMISSAO")
@AllArgsConstructor
@NoArgsConstructor
public class SubmissaoModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long tempoExecucao;

    private String status;

    private String entrada;

    private String saida;
}
