package com.tcc.joaomyrlla.appcode2know.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CasoDeTesteDTO {
  private Long id;

  @NotBlank(message = "O campo caso é obrigatório")
  private int caso;

  @NotBlank(message = "O campo entrada é obrigatório")
  private String entrada;

  @NotBlank(message = "O campo saída é obrigatório")
  private String saida;

  @NotBlank(message = "O campo problemaId é obrigatório")
  private Long problemaId;
}