package com.tcc.joaomyrlla.appcode2know.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditSenhaDTO {
    private String senhaAtual;

    private String novaSenha;
}
