package com.tcc.joaomyrlla.appcode2know.dto;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter

public class TurmaDTO {

  private Long id;

  @NotBlank(message = "O atributo data de abertura é obrigatório")
  private Date dtAbertura;

  @NotBlank(message = "O atributo data de encerramento é obrigatório")
  private Date dtEncerramento;

  private String titulo;

  @NotBlank(message = "O atributo nome da turma é obrigatório")
  private String nomeTurma;

  @NotBlank(message = "O atributo semestre é obrigatório")
  private String Semestre;

  private Long instituicaoId;

}