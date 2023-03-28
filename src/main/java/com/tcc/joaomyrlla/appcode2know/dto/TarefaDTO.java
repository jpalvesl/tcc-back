package com.tcc.joaomyrlla.appcode2know.dto;

import java.sql.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefaDTO {

    private Long id;

    @NotNull(message = "O atributo data de abertura é obrigatório")
    private Date dtAbertura;

    @NotNull(message = "O atributo data de encerramento é obrigatório")
    private Date dtEncerramento;

    @NotBlank(message = "O atributo descrição é obrigatório")
    private String descricao;

    @NotBlank(message = "O atributo título é obrigatório")
    private String titulo;

    @NotNull
    private Long criadorId;

    @NotNull
    private Long turmaId;
}