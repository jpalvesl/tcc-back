package com.tcc.joaomyrlla.appcode2know.contants;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StatusEnum {
    CORRETA("", ""),
    ERRADA("", ""),
    ERRO_EXECUCAO("", ""),
    TEMPO_LIMITE_EXCEDIDO("", ""),
    ERRO_APRESENTACAO("", "");



    private String status;
    private String codigo;

}
