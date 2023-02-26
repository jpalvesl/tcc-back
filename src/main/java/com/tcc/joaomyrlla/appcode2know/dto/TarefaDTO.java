package com.tcc.joaomyrlla.appcode2know.dto;
import java.sql.Date;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TarefaDTO {

  private Long id;

    @NotBlank(message = "O atributo data de abertura é obrigatório")
    private Date dtAbertura;
    
    @NotBlank(message = "O atributo data de encerramento é obrigatório")
    private Date dtEncerramento;

    @NotBlank(message = "O atributo descrição é obrigatório")
    private String descricao;

    @NotBlank(message = "O atributo título é obrigatório")
    private String titulo;

    private Long criadorId;

    private Long turmaId;

}