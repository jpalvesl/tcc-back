package com.tcc.joaomyrlla.appcode2know.dto;

import com.tcc.joaomyrlla.appcode2know.model.RespostaCasoTeste;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespostaDeCasoTesteDTO {
    private Long id;

    @NotNull(message = "O campo caso é obrigatório")
    private int caso;

    @NotBlank(message = "O campo entrada é obrigatório")
    private String entrada;

    @NotBlank(message = "O campo saída é obrigatório")
    private String saida;

    @NotNull(message = "O campo problemaId é obrigatório")
    private Long submissaoId;

    private double tempo;

    private String status;

    @NotBlank(message = "O campo linguagem é obrigatório")
    private String linguagem;

    public static RespostaDeCasoTesteDTO toRespostaCasoDeTesteDTO(RespostaCasoTeste respostaCasoTeste) {
        RespostaDeCasoTesteDTO respostaDeCasoTesteDTO = new RespostaDeCasoTesteDTO();
        BeanUtils.copyProperties(respostaCasoTeste, respostaDeCasoTesteDTO);
        respostaDeCasoTesteDTO.setSubmissaoId(respostaCasoTeste.getSubmissao().getId());

        return respostaDeCasoTesteDTO;
    }
}
