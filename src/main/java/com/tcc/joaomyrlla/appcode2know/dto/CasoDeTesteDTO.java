package com.tcc.joaomyrlla.appcode2know.dto;

import com.tcc.joaomyrlla.appcode2know.model.CasoDeTeste;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CasoDeTesteDTO {
  private Long id;

  @NotNull(message = "O campo caso é obrigatório")
  private int caso;

  @NotBlank(message = "O campo entrada é obrigatório")
  private String entrada;

  @NotBlank(message = "O campo saída é obrigatório")
  private String saida;

  @NotNull(message = "O campo problemaId é obrigatório")
  private Long problemaId;

  public static CasoDeTesteDTO toCasoDeTesteDTO(CasoDeTeste casoDeTeste) {
    CasoDeTesteDTO casoDeTesteDTO = new CasoDeTesteDTO();
    BeanUtils.copyProperties(casoDeTeste, casoDeTesteDTO);
    casoDeTesteDTO.setProblemaId(casoDeTeste.getProblema().getId());

    return casoDeTesteDTO;
  }
}