package com.tcc.joaomyrlla.appcode2know.dto;

import com.tcc.joaomyrlla.appcode2know.model.Topico;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicoDTO {
    private Long id;

    @NotBlank(message = "O nome do tópico não pode ser vazio")
    private String nome;

    public static TopicoDTO toTopicoDTO(Topico topico) {
        TopicoDTO topicoDTO = new TopicoDTO();
        BeanUtils.copyProperties(topico, topicoDTO);

        return topicoDTO;
    }
}
