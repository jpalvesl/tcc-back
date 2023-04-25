package com.tcc.joaomyrlla.appcode2know.dto;

import com.tcc.joaomyrlla.appcode2know.model.Submissao;
import com.tcc.joaomyrlla.appcode2know.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissaoDTO {
    private Long id;

    private String codigoResposta;

    private Long problemaId;

    private Long usuarioId;

    private String status;

    private String data;

    private double tempoMedio;

    private String linguagem;

    public static SubmissaoDTO toSubmissaoDTO(Submissao submissao) {
        SubmissaoDTO submissaoDTO = new SubmissaoDTO();
        BeanUtils.copyProperties(submissao, submissaoDTO);
        submissaoDTO.setProblemaId(submissao.getProblema().getId());
        submissaoDTO.setUsuarioId(submissao.getUsuario().getId());

        submissaoDTO.setData(DateUtils.toPattern("dd/MM/yyy", submissao.getData()));
        return submissaoDTO;
    }
}
