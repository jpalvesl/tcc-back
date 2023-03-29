package com.tcc.joaomyrlla.appcode2know.dto;

import com.tcc.joaomyrlla.appcode2know.model.multivalorado.Topico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicoDTO {
    private Long id;

    private String nome;

    public static TopicoDTO toTopicoDTO(Topico topico) {
        TopicoDTO topicoDTO = new TopicoDTO();
        BeanUtils.copyProperties(topico, topicoDTO);

        return topicoDTO;
    }
}
