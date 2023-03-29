package com.tcc.joaomyrlla.appcode2know.dto;

import com.tcc.joaomyrlla.appcode2know.model.Problema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

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

  public static ProblemaDTO toProblemaDTO(Problema problema) {
    ProblemaDTO problemaDTO = new ProblemaDTO();
    BeanUtils.copyProperties(problema, problemaDTO);
    problemaDTO.setCriadorId(problema.getCriador().getId());

    return problemaDTO;
  }
}