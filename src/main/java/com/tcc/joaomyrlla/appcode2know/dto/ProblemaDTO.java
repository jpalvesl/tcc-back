package com.tcc.joaomyrlla.appcode2know.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProblemaDTO {
  private Long id;

  @NotBlank(message = "O atributo descricao é obrigatório")
  private String descricao;
  
  @NotBlank(message = "")
  private int dificuldade;
  
  @NotBlank(message = "")
  private String fonte;
  
  @NotBlank(message = "")
  private String nome;
  
  @NotBlank(message = "")
  private String textoEntrada;
  
  @NotBlank(message = "")
  private String textoSaida;
  
  @NotBlank(message = "")
  private Long criadorId;
}