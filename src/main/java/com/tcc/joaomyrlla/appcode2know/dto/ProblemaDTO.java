package com.tcc.joaomyrlla.appcode2know.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemaDTO {
  private Long id;

  @NotBlank(message = "O atributo descricao é obrigatório")
  private String descricao;
  
  @NotNull(message = "")
  private int dificuldade;
  
  @NotBlank(message = "")
  private String fonte;
  
  @NotBlank(message = "")
  private String nome;
  
  @NotBlank(message = "")
  private String textoEntrada;
  
  @NotBlank(message = "")
  private String textoSaida;
  
  @NotNull(message = "")
  private Long criadorId;
}