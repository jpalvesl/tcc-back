package com.tcc.joaomyrlla.appcode2know.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissaoDTO {
    private Long id;

    private String codigoResposta;

    private Long problemaId;

    private Long usuarioId;

    private String status;
}
