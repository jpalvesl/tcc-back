package com.tcc.joaomyrlla.appcode2know.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissaoDTO {
    public String codigoResposta;

    public String problemaId;

    public String UsuarioId;
}
