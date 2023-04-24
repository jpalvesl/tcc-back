package com.tcc.joaomyrlla.appcode2know.dto;

import com.tcc.joaomyrlla.appcode2know.model.Problema;
import com.tcc.joaomyrlla.appcode2know.model.Topico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

  private String autor;

  private boolean problemaDeProva;

  @NotNull
  private double tempoLimite;

  @NotNull
  private Integer limiteDeMemoria;

  @NotNull
  private List<HashMap<String, Object>> topicos = new ArrayList<>();

  public static ProblemaDTO toProblemaDTO(Problema problema) {
    ProblemaDTO problemaDTO = new ProblemaDTO();
    BeanUtils.copyProperties(problema, problemaDTO);
    problemaDTO.setCriadorId(problema.getCriador().getId());
    problemaDTO.setAutor(problema.getCriador().getNome());

    problema.getTopicos().forEach(topico -> {
      HashMap<String, Object> mapTopico = new HashMap<>();
      mapTopico.put("id", topico.getId());
      mapTopico.put("nome", topico.getNome());

      problemaDTO.getTopicos().add(mapTopico);
    });

    return problemaDTO;
  }
}