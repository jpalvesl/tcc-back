package com.tcc.joaomyrlla.appcode2know.dto;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstituicaoDTO {

  private Long id;

  @NotBlank(message = "O atributo nome é obrigatório")
  private String nome;

  @NotBlank(message = "O atributo campus é obrigatório")
  private String campus;

}